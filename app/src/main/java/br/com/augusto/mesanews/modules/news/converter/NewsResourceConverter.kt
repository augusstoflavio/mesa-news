package br.com.augusto.mesanews.modules.news.converter

import br.com.augusto.mesanews.app.database.Database
import br.com.augusto.mesanews.modules.news.data.FavoriteNews
import br.com.augusto.mesanews.modules.news.data.News
import br.com.augusto.mesanews.modules.news.data.NewsResource

class NewsResourceConverter {

    companion object {

        fun toList(list: List<NewsResource>): List<News> {
            return list.map { toNews(it) }
        }

        fun toNews(resource: NewsResource): News {

            val realm = Database.getInstance()
                val favorites = realm.where(FavoriteNews::class.java)
                            .equalTo("title", resource.title).findAll()

                val favorite = favorites.isNotEmpty()
            realm.close()

            val news = News()
            news.title = resource.title
            news.description = resource.description
            news.content = resource.content
            news.author = resource.author
            news.publishedAt = resource.publishedAt
            news.highlight = resource.highlight
            news.url = resource.url
            news.imageUrl = resource.imageUrl
            news.favorite = favorite
            return news
        }
    }
}