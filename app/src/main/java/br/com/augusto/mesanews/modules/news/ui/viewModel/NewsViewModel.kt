package br.com.augusto.mesanews.modules.news.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.augusto.mesanews.app.data.Result
import br.com.augusto.mesanews.modules.news.data.News
import br.com.augusto.mesanews.modules.news.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(
    val newsRepository: NewsRepository
): ViewModel() {

    val news: MutableLiveData<MutableList<News>> = MutableLiveData(mutableListOf())
    val highliht: MutableLiveData<List<News>> = MutableLiveData(mutableListOf())

    init {
        refresh()
    }

    fun refresh() {
        getNews(1)
        getHighlights()
    }

    fun getNews(page: Int?) {
        viewModelScope.launch {
            val result = newsRepository.getNews(page)
            if (result is Result.Success) {
                news.value!!.addAll(result.data)
                news.postValue(news.value)
            }
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