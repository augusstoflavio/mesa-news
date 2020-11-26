package br.com.augusto.mesanews.modules.news.repository

import br.com.augusto.mesanews.app.data.Result
import br.com.augusto.mesanews.app.database.Database
import br.com.augusto.mesanews.modules.news.converter.NewsResourceConverter
import br.com.augusto.mesanews.modules.news.data.FavoriteNews
import br.com.augusto.mesanews.modules.news.data.News
import br.com.augusto.mesanews.modules.news.service.NewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository(val newsService: NewsService) {


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

    suspend fun changeFavoriteSituation(news: News): Result<News> {
        return withContext(Dispatchers.IO) {
            val realm = Database.getInstance()
            try {
                realm.beginTransaction()

                val favorites = realm.where(FavoriteNews::class.java)
                    .equalTo("title", news.title).findAll()

                if (favorites.isEmpty()) {
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
                } else {
                    favorites.forEach {
                        it.deleteFromRealm()
                    }
                }

                realm.commitTransaction()

                news.favorite = !news.favorite
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
        val news = realm.copyFromRealm(realm.where(FavoriteNews::class.java)
            .findAll()).map {
            News(
                title = it.title!!,
                description = it.description!!,
                content = it.content!!,
                author = it.author!!,
                publishedAt = it.publishedAt!!,
                highlight = it.highlight!!,
                url= it.url!!,
                imageUrl = it.imageUrl,
                favorite = true
            )
        }
        realm.close()

        return news
    }

}