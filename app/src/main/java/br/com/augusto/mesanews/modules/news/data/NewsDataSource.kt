package br.com.augusto.mesanews.modules.news.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PageKeyedDataSource
import br.com.augusto.mesanews.app.data.resources.GetListResponse
import br.com.augusto.mesanews.modules.news.converter.NewsResourceConverter
import br.com.augusto.mesanews.modules.news.service.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import br.com.augusto.mesanews.app.data.Result


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
                callback.onResult(NewsResourceConverter.toList(response.body()!!.data), null, 2)
            }

            override fun onFailure(call: Call<GetListResponse<List<NewsResource>>>, t: Throwable) {
                loading.postValue(Result.Error(Exception("Problema de conexão")))
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        loading.postValue(Result.Success(true))
        newsService.news(params.key).enqueue(object : Callback<GetListResponse<List<NewsResource>>> {
            override fun onResponse(
                call: Call<GetListResponse<List<NewsResource>>>,
                response: Response<GetListResponse<List<NewsResource>>>
            ) {
                loading.postValue(Result.Success(false))
                callback.onResult(NewsResourceConverter.toList(response.body()!!.data), params.key + 1)
            }

            override fun onFailure(call: Call<GetListResponse<List<NewsResource>>>, t: Throwable) {
                loading.postValue(Result.Error(Exception("Problema de conexão")))
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
    }

}