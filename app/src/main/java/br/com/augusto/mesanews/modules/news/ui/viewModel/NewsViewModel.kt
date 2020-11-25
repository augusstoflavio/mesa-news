package br.com.augusto.mesanews.modules.news.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import br.com.augusto.mesanews.app.data.Result
import br.com.augusto.mesanews.modules.news.data.News
import br.com.augusto.mesanews.modules.news.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(
    val newsRepository: NewsRepository
): ViewModel() {

    val highliht: MutableLiveData<List<News>> = MutableLiveData(mutableListOf())
    var news: LiveData<PagedList<News>> = newsRepository.getNews()

    init {
        refresh()
    }

    fun refresh() {
        getHighlights()
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