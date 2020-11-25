package br.com.augusto.mesanews.modules.news.data

import NewsDataSource
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import br.com.augusto.mesanews.modules.news.service.NewsService

class NewsDataSourceFactory(val newsService: NewsService): DataSource.Factory<Int, News>() {

    val newsDataSourceLiveData = MutableLiveData<NewsDataSource>()

    override fun create(): DataSource<Int, News> {
        val newsDataSource = NewsDataSource(newsService)
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}