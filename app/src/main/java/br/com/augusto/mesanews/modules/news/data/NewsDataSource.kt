package br.com.augusto.mesanews.modules.news.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import br.com.augusto.mesanews.app.data.Result
import br.com.augusto.mesanews.app.data.resources.ResponseResource
import br.com.augusto.mesanews.app.database.Database
import br.com.augusto.mesanews.modules.news.service.NewsService
import io.realm.Sort
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsDataSource(val newsService: NewsService) : PageKeyedDataSource<Int, News>() {

    var loading: MutableLiveData<Result<Boolean>> = MutableLiveData(null)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, News>
    ) {
        loading.postValue(Result.Success(true))
        newsService.news(1).enqueue(object : Callback<br.com.augusto.mesanews.app.data.resources.ResponseResource<List<NewsResource>>> {
            override fun onResponse(
                call: Call<br.com.augusto.mesanews.app.data.resources.ResponseResource<List<NewsResource>>>,
                responseResource: Response<br.com.augusto.mesanews.app.data.resources.ResponseResource<List<NewsResource>>>
            ) {
                loading.postValue(Result.Success(false))
                val listNews = convertToList(responseResource.body()!!.data)
                clearNewsNotFavorite()
                saveNews(listNews)
                callback.onResult(listNews, null, 2)
            }

            override fun onFailure(call: Call<br.com.augusto.mesanews.app.data.resources.ResponseResource<List<NewsResource>>>, t: Throwable) {
                loading.postValue(Result.Error(Exception("Problema de conexão")))
                callback.onResult(getNews(), null, 2)
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        loading.postValue(Result.Success(true))
        newsService.news(params.key).enqueue(object :
            Callback<br.com.augusto.mesanews.app.data.resources.ResponseResource<List<NewsResource>>> {
            override fun onResponse(
                call: Call<br.com.augusto.mesanews.app.data.resources.ResponseResource<List<NewsResource>>>,
                responseResource: Response<ResponseResource<List<NewsResource>>>
            ) {
                loading.postValue(Result.Success(false))
                val listNews = convertToList(responseResource.body()!!.data)
                saveNews(listNews)
                callback.onResult(
                    listNews,
                    params.key + 1
                )
            }

            override fun onFailure(call: Call<br.com.augusto.mesanews.app.data.resources.ResponseResource<List<NewsResource>>>, t: Throwable) {
                loading.postValue(Result.Error(Exception("Problema de conexão")))
            }
        })
    }

    private fun getNews(): MutableList<News> {
        val realm = Database.getInstance()
        val news = realm.copyFromRealm(
            realm.where(News::class.java).sort("publishedAt", Sort.DESCENDING).findAll()
        )
        realm.close()
        return news ?: mutableListOf()
    }

    private fun clearNewsNotFavorite() {
        val realm = Database.getInstance()
        realm.beginTransaction()
            realm.where(News::class.java)
                    .equalTo("favorite", false)
                    .findAll()
                    .deleteAllFromRealm()
        realm.commitTransaction()
        realm.close()
    }

    private fun saveNews(listNews: List<News>) {
        val realm = Database.getInstance()
        realm.beginTransaction()
            realm.copyToRealmOrUpdate(listNews)
        realm.commitTransaction()
        realm.close()
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
    }

    private fun convertToList(resources: List<NewsResource>): List<News> {
        val realm = Database.getInstance()
        val listNews = resources.map {
            val resource = it

            val equivalentNews = realm.where(News::class.java)
                .equalTo("title", it.title)
                .findFirst()

            var favorite = false
            if (equivalentNews != null) {
                favorite = equivalentNews.favorite
            }

            val news = News()
            news.title = resource.title
            news.description = resource.description
            news.content = resource.content
            news.author = resource.author
            news.publishedAt = resource.publishedAt
            news.highlight = resource.highlight
            news.url = resource.url
            news.imageUrl = resource.imageUrl
            news.favorite = favorite
            return@map news
        }

        realm.close()
        return listNews
    }

}