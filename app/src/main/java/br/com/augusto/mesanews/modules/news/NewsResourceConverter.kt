package br.com.augusto.mesanews.modules.news

import br.com.augusto.mesanews.modules.news.data.News
import br.com.augusto.mesanews.modules.news.data.NewsResource

class NewsResourceConverter {

    companion object {

        fun toList(list: List<NewsResource>): List<News> {
            return list.map { toNews(it) }
        }

        fun toNews(resource: NewsResource): News {
            return News(
                title = resource.title,
                description = resource.description,
                content = resource.content,
                author = resource.author,
                publishedAt = resource.publishedAt,
                highlight = resource.highlight,
                url = resource.url,
                imageUrl = resource.imageUrl
            )
        }
    }
}