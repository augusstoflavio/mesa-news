package br.com.augusto.mesanews.modules.news.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.augusto.mesanews.app.data.Result
import br.com.augusto.mesanews.modules.news.data.News
import br.com.augusto.mesanews.modules.news.repository.NewsRepository
import kotlinx.coroutines.launch

class FavoriteNewsViewModel(
    val newsRepository: NewsRepository
): ViewModel() {

    var favoriteNews = MutableLiveData(listOf<News>())

    fun getFavoriteNews() {
        favoriteNews.value = newsRepository.getFavoriteNews()
    }
}