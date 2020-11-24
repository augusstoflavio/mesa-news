package br.com.augusto.mesanews.modules.news.repository

import br.com.augusto.mesanews.modules.news.data.News
import br.com.augusto.mesanews.modules.news.service.NewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import br.com.augusto.mesanews.app.data.Result
import br.com.augusto.mesanews.modules.news.NewsResourceConverter

class NewsRepository(val newsService: NewsService) {

    suspend fun getNews(page: Int?): Result<List<News>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = newsService.news().execute()

                val body = result.body()

                return@withContext Result.Success(
                    NewsResourceConverter.toList(body?.data!!)
                )
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }
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