package br.com.augusto.mesanews.modules.news.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.ui.dialog.FullScreenDialog
import br.com.augusto.mesanews.modules.news.ui.adapter.FavoriteNewsAdapter
import br.com.augusto.mesanews.modules.news.ui.adapter.NewsAdapterClickListener
import br.com.augusto.mesanews.modules.news.ui.viewModel.FavoriteNewsViewModel
import br.com.augusto.mesanews.modules.news.ui.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.activity_favorite_news.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteNewsDialog: FullScreenDialog() {

    val newsViewModel: NewsViewModel by sharedViewModel()
    val favoriteNewsViewModel: FavoriteNewsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapterNews = FavoriteNewsAdapter(activity as NewsAdapterClickListener)
        favorite_news.adapter = adapterNews
        favorite_news.layoutManager = LinearLayoutManager(context)

        favoriteNewsViewModel.favoriteNews.observe(this, {
            adapterNews.update(it)
        })

        newsViewModel.newsFavoriteUpdateResult.observe(this, {
            favoriteNewsViewModel.getFavoriteNews()
        })
    }

    override fun onResume() {
        super.onResume()
        favoriteNewsViewModel.getFavoriteNews()
    }

    override fun getDialogTitle(): String {
        return "Favorite news"
    }

    override fun getContent(): Int {
        return R.layout.activity_favorite_news
    }
}