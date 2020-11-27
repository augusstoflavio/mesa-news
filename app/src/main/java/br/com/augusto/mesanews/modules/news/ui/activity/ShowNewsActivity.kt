package br.com.augusto.mesanews.modules.news.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ShareCompat
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.data.Result
import br.com.augusto.mesanews.app.helper.toast
import br.com.augusto.mesanews.app.ui.WebViewProgressClient
import br.com.augusto.mesanews.app.ui.activity.ToolbarActivity
import br.com.augusto.mesanews.modules.news.data.News
import br.com.augusto.mesanews.modules.news.ui.viewModel.ShowNewsViewModel
import kotlinx.android.synthetic.main.dialog_view_news.*
import org.koin.android.viewmodel.ext.android.viewModel

class ShowNewsActivity: ToolbarActivity() {

    private lateinit var favoriteMenu: MenuItem
    private lateinit var news: News
    val viewModel: ShowNewsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_view_news)


        val extras = intent.extras
        if (extras == null) {
            finish()
            return
        }

        if (extras.getSerializable("news") == null) {
            finish()
            return
        }

        news = extras.getSerializable("news") as News

        setTitle(news.title)
        web_view.settings.javaScriptEnabled = true
        web_view.webViewClient = WebViewProgressClient(progress)
        web_view.loadUrl(news.url)

        viewModel.newsFavoriteUpdateResult.observe(this, {
            if (it == null) {
                return@observe
            }

            if (it is Result.Success) {
                news = it.data
                changeIconButtonFavorite()
            } else if(it is Result.Error) {
                toast(it.exception.message!!)
            }

            viewModel.newsFavoriteUpdateResult.value = null
        })
    }

    private fun changeIconButtonFavorite() {
        if (news.favorite) {
            favoriteMenu.setIcon(R.drawable.ic_heart_red)
        } else {
            favoriteMenu.setIcon(R.drawable.ic_heart)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val sharedMenu = menu?.add(1, MENU_SHARED, Menu.NONE, "Shared")
        sharedMenu!!.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        sharedMenu.setIcon(R.drawable.ic_baseline_share_24)

        favoriteMenu = menu.add(1, MENU_FAVORITE, Menu.NONE, "Favorite")
        favoriteMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        changeIconButtonFavorite()

        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        private const val MENU_SHARED = 1
        private const val MENU_FAVORITE = 2
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == MENU_SHARED) {
            sharedView(news)
        }
        if (item.itemId == MENU_FAVORITE) {
            favoriteView(news)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun favoriteView(news: News) {
        viewModel.favoriteNews(news)
    }

    private fun sharedView(news: News) {
        ShareCompat.IntentBuilder.from(this)
            .setType("text/plain")
            .setChooserTitle(news.title)
            .setText(news.url)
            .setSubject(news.content)
            .startChooser()
    }
}