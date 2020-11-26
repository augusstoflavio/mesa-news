package br.com.augusto.mesanews.modules.news.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import br.com.augusto.mesanews.app.data.Result
import br.com.augusto.mesanews.app.data.resources.GetListResponse
import br.com.augusto.mesanews.app.database.Database
import br.com.augusto.mesanews.modules.news.converter.NewsResourceConverter
import br.com.augusto.mesanews.modules.news.service.NewsService
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
        newsService.news(1).enqueue(object : Callback<GetListResponse<List<NewsResource>>> {
            override fun onResponse(
                call: Call<GetListResponse<List<NewsResource>>>,
                response: Response<GetListResponse<List<NewsResource>>>
            ) {
                loading.postValue(Result.Success(false))
                val listNews = convertToList(response.body()!!.data)
                clearNews()
                saveNews(listNews)
                callback.onResult(listNews, null, 2)
            }

            override fun onFailure(call: Call<GetListResponse<List<NewsResource>>>, t: Throwable) {
                loading.postValue(Result.Error(Exception("Problema de conexão")))
                callback.onResult(getNews(), null, 2)
            }
        })
    }

    private fun getNews(): MutableList<News> {
        val realm = Database.getInstance()
        val news = realm.copyFromRealm(
            realm.where(News::class.java).findAll()
        )
        realm.close()
        return news ?: mutableListOf()
    }

    private fun clearNews() {
        val realm = Database.getInstance()
        realm.beginTransaction()
        realm.delete(News::class.java)
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

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        loading.postValue(Result.Success(true))
        newsService.news(params.key).enqueue(object :
            Callback<GetListResponse<List<NewsResource>>> {
            override fun onResponse(
                call: Call<GetListResponse<List<NewsResource>>>,
                response: Response<GetListResponse<List<NewsResource>>>
            ) {
                loading.postValue(Result.Success(false))
                callback.onResult(
                    NewsResourceConverter.toList(response.body()!!.data),
                    params.key + 1
                )
            }

            override fun onFailure(call: Call<GetListResponse<List<NewsResource>>>, t: Throwable) {
                loading.postValue(Result.Error(Exception("Problema de conexão")))
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
    }

    private fun convertToList(resources: List<NewsResource>): List<News> {
        val realm = Database.getInstance()
        val listNews = resources.map {
            val resource = it

            val news = News()
            news.title = resource.title
            news.description = resource.description
            news.content = resource.content
            news.author = resource.author
            news.publishedAt = resource.publishedAt
            news.highlight = resource.highlight
            news.url = resource.url
            news.imageUrl = resource.imageUrl
            return@map news
        }

        realm.close()
        return listNews
    }

}