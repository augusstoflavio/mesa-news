package br.com.augusto.mesanews.modules.news.data

import androidx.paging.PageKeyedDataSource
import br.com.augusto.mesanews.modules.news.service.NewsService

class NewsDataSource(newsService: NewsService): PageKeyedDataSource<Long, News>() {

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, News>
    ) {
        TODO("Not yet implemented")
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, News>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, News>) {
        TODO("Not yet implemented")
    }
}