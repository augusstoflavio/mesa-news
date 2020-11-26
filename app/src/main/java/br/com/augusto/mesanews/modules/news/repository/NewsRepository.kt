package br.com.augusto.mesanews.modules.news.repository

import br.com.augusto.mesanews.app.data.Result
import br.com.augusto.mesanews.app.database.Database
import br.com.augusto.mesanews.modules.news.converter.NewsResourceConverter
import br.com.augusto.mesanews.modules.news.data.News
import br.com.augusto.mesanews.modules.news.service.NewsService
import io.realm.Sort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository(val newsService: NewsService) {


    private fun getHighlightsCache(): MutableList<News> {
        val realm = Database.getInstance()
        val news = realm.copyFromRealm(
            realm.where(News::class.java)
                 .equalTo("highlight", true)
                 .sort("publishedAt", Sort.DESCENDING).findAll()
        )
        realm.close()
        return news ?: mutableListOf()
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
                return@withContext Result.Success(
                    getHighlightsCache()
                )
            }
        }
    }

    suspend fun changeFavoriteSituation(news: News): Result<News> {
        return withContext(Dispatchers.IO) {
            val realm = Database.getInstance()
            try {
                realm.beginTransaction()

                news.favorite = !news.favorite
                realm.copyToRealmOrUpdate(news)

                realm.commitTransaction()
                return@withContext Result.Success(news)
            } catch (e: Exception) {
                realm.cancelTransaction()
                return@withContext Result.Error(e)
            } finally {
                realm.close()
            }
        }
    }

    fun getFavoriteNews(): List<News>? {
        val realm = Database.getInstance()
        val news = realm.copyFromRealm(
            realm.where(News::class.java)
                .equalTo("favorite", true)
                .findAll()
        )
        realm.close()

        return news
    }

}