package br.com.augusto.mesanews.modules.news.ui.dialog

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.data.Result
import br.com.augusto.mesanews.app.ui.WebViewProgressClient
import br.com.augusto.mesanews.modules.news.data.News
import br.com.augusto.mesanews.modules.news.ui.activity.NewsActivity
import br.com.augusto.mesanews.modules.news.ui.viewModel.NewsViewModel
import br.com.marata.rastreamento.app.ui.dialog.FullScreenDialog
import kotlinx.android.synthetic.main.dialog_view_news.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ViewNewsDialog: FullScreenDialog() {

    private lateinit var favoriteMenu: MenuItem

    private lateinit var news: News
    val newsViewModel: NewsViewModel by sharedViewModel()

    override fun getDialogTitle(): String {
        return getString(R.string.news)
    }

    override fun getContent(): Int {
        return R.layout.dialog_view_news
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        news = arguments?.get("news") as News
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(news.title!!)

        web_view.settings.javaScriptEnabled = true
        web_view.webViewClient = WebViewProgressClient(progress)
        web_view.loadUrl(news.url)

        initMenu()

        newsViewModel.newsFavoriteUpdateResult.observe(this, {
            if (it is Result.Success && it.data.title == news.title) {
                news = it.data
                changeIconButtonFavorite()
            }
        })
    }

    private fun initMenu() {
        val sharedMenu = toolbar.menu?.add(1, MENU_SHARED, Menu.NONE, "Shared")
        sharedMenu!!.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        sharedMenu.setIcon(R.drawable.ic_baseline_share_24)
        sharedMenu.setOnMenuItemClickListener {
            val a = activity as NewsActivity
            a.onSharedClick(news)
            return@setOnMenuItemClickListener true
        }

        favoriteMenu = toolbar.menu.add(1, MENU_FAVORITE, Menu.NONE, "Favorite")
        favoriteMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        favoriteMenu.setOnMenuItemClickListener {
            newsViewModel.favoriteNews(news)
            return@setOnMenuItemClickListener true
        }
        changeIconButtonFavorite()
    }

    companion object {
        private const val MENU_SHARED = 1
        private const val MENU_FAVORITE = 2
    }

    private fun changeIconButtonFavorite() {
        if (news.favorite) {
            favoriteMenu.setIcon(R.drawable.ic_heart_red)
        } else {
            favoriteMenu.setIcon(R.drawable.ic_heart)
        }
    }
}