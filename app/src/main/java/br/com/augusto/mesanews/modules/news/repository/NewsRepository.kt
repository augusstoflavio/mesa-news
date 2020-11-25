package br.com.augusto.mesanews.modules.news.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.augusto.mesanews.modules.news.data.News
import br.com.augusto.mesanews.modules.news.service.NewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import br.com.augusto.mesanews.app.data.Result
import br.com.augusto.mesanews.app.database.Database
import br.com.augusto.mesanews.modules.news.converter.NewsResourceConverter
import br.com.augusto.mesanews.modules.news.data.FavoriteNews
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

    suspend fun favorite(news: News) {
        return withContext(Dispatchers.IO) {
            val realm = Database.getInstance()
            try {
                realm.beginTransaction()

                val favoriteNews = FavoriteNews()
                favoriteNews.title = news.title
                favoriteNews.author = news.author
                favoriteNews.content = news.content
                favoriteNews.description = news.description
                favoriteNews.highlight = news.highlight
                favoriteNews.imageUrl = news.imageUrl
                favoriteNews.publishedAt = news.publishedAt
                favoriteNews.url = news.url
                realm.copyToRealmOrUpdate(
                    favoriteNews
                )

                realm.commitTransaction()
            } catch (e: Exception) {
                realm.cancelTransaction()
            } finally {
                realm.close()
            }
        }
    }

}