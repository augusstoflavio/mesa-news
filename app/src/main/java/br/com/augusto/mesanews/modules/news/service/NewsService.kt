package br.com.augusto.mesanews.modules.news.service

import br.com.augusto.mesanews.app.data.resources.ResponseResource
import br.com.augusto.mesanews.modules.news.data.NewsResource
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET(" /v1/client/news")
    fun news(@Query("current_page") currentPage: Int): Call<ResponseResource<List<NewsResource>>>

    @GET("/v1/client/news/highlights")
    fun highlights(): Call<ResponseResource<List<NewsResource>>>
}