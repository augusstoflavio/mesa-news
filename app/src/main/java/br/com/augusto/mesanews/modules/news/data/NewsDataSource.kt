package br.com.augusto.mesanews.modules.news.data

import androidx.paging.PageKeyedDataSource
import br.com.augusto.mesanews.app.data.resources.GetListResponse
import br.com.augusto.mesanews.modules.news.converter.NewsResourceConverter
import br.com.augusto.mesanews.modules.news.service.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsDataSource(val newsService: NewsService) : PageKeyedDataSource<Int, News>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, News>
    ) {
        newsService.news(1).enqueue(object : Callback<GetListResponse<List<NewsResource>>> {
            override fun onResponse(
                call: Call<GetListResponse<List<NewsResource>>>,
                response: Response<GetListResponse<List<NewsResource>>>
            ) {
                callback.onResult(NewsResourceConverter.toList(response.body()!!.data), null, 2)
            }

            override fun onFailure(call: Call<GetListResponse<List<NewsResource>>>, t: Throwable) {
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        newsService.news(params.key).enqueue(object : Callback<GetListResponse<List<NewsResource>>> {
            override fun onResponse(
                call: Call<GetListResponse<List<NewsResource>>>,
                response: Response<GetListResponse<List<NewsResource>>>
            ) {
                callback.onResult(NewsResourceConverter.toList(response.body()!!.data), params.key + 1)
            }

            override fun onFailure(call: Call<GetListResponse<List<NewsResource>>>, t: Throwable) {
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
    }

}