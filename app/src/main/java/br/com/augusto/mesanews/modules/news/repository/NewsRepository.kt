package br.com.augusto.mesanews.modules.news.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.augusto.mesanews.modules.news.data.News
import br.com.augusto.mesanews.modules.news.service.NewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import br.com.augusto.mesanews.app.data.Result
import br.com.augusto.mesanews.modules.news.converter.NewsResourceConverter
import br.com.augusto.mesanews.modules.news.data.NewsDataSourceFactory

class NewsRepository(val newsService: NewsService, val newsDataSourceFactory: NewsDataSourceFactory) {


    fun getNews(): LiveData<PagedList<News>> {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(20 * 2)
            .setEnablePlaceholders(false)
            .build()
        return LivePagedListBuilder(newsDataSourceFactory, config).build()
    }

    suspend fun getHighlights(): Result<List<News>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = newsService.highlights().execute()

                val body = result.body()

                return@withContext Result.Success(
                    NewsResourceConverter.toList(body?.data!!)
                )
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }
    }
}