package br.com.augusto.mesanews.modules.news.ui.viewModel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.augusto.mesanews.app.data.Result
import br.com.augusto.mesanews.modules.news.data.News
import br.com.augusto.mesanews.modules.news.data.NewsDataSource
import br.com.augusto.mesanews.modules.news.data.NewsDataSourceFactory
import br.com.augusto.mesanews.modules.news.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(
    val newsRepository: NewsRepository,
    val newsDataSourceFactory: NewsDataSourceFactory
): ViewModel() {

    val highliht: MutableLiveData<List<News>> = MutableLiveData(mutableListOf())
    var newsFavoriteUpdateResult: MutableLiveData<Result<News>> = MutableLiveData(null)
    var loadingNews: LiveData<Result<Boolean>> = Transformations.switchMap(newsDataSourceFactory.newsDataSourceLiveData, NewsDataSource::loading)

    init {
        refresh()
    }

    fun refresh() {
        getHighlights()
    }

    fun getNews(): LiveData<PagedList<News>> {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(20 * 2)
            .setEnablePlaceholders(false)
            .build()

        return LivePagedListBuilder(newsDataSourceFactory, config).build()
    }

    fun favoriteNews(news: News) {
        viewModelScope.launch {
            newsFavoriteUpdateResult.value = newsRepository.changeFavoriteSituation(news)
        }
    }

    private fun getHighlights() {
        viewModelScope.launch {
            val result = newsRepository.getHighlights()
            if (result is Result.Success) {
                highliht.value = result.data
            }
        }
    }
}