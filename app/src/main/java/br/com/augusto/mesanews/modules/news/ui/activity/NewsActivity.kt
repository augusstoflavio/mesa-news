package br.com.augusto.mesanews.modules.news.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.data.Result
import br.com.augusto.mesanews.app.helper.isVisible
import br.com.augusto.mesanews.app.helper.startDialog
import br.com.augusto.mesanews.app.helper.toast
import br.com.augusto.mesanews.modules.news.data.News
import br.com.augusto.mesanews.modules.news.ui.adapter.HighlightsAdapter
import br.com.augusto.mesanews.modules.news.ui.adapter.NewsAdapter
import br.com.augusto.mesanews.modules.news.ui.adapter.NewsAdapterClickListener
import br.com.augusto.mesanews.modules.news.ui.dialog.FavoriteNewsDialog
import br.com.augusto.mesanews.modules.news.ui.dialog.ViewNewsDialog
import br.com.augusto.mesanews.modules.news.ui.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.app_bar.*
import org.jetbrains.anko.support.v4.onRefresh
import org.koin.android.viewmodel.ext.android.viewModel


class NewsActivity : AppCompatActivity(), NewsAdapterClickListener {

    private lateinit var adapterNews: NewsAdapter
    val viewModel: NewsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        setSupportActionBar(toolbar_app)
        setTitle(getString(R.string.news))

        initNewsList()
        initHighlightsList()
        initLoadings()
        initFavoriteObserver()
        initSwipe()
    }

    private fun initFavoriteObserver() {
        viewModel.newsFavoriteUpdateResult.observe(this, {
            if (it == null) {
                return@observe
            }

            if (it is Result.Success) {
                adapterNews.changeFavState(it.data)
            } else if (it is Result.Error) {
                toast(it.exception.message!!)
            }

//            viewModel.newsFavoriteUpdateResult.value = null
        })
    }

    private fun initHighlightsList() {
        val highlightsAdapter = HighlightsAdapter(this)
        highlights.adapter = highlightsAdapter
        highlights.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        viewModel.highliht.observe(this, {
            highlightsAdapter.update(it)
        })
    }

    private fun initNewsList() {
        adapterNews = NewsAdapter(this)
        news.adapter = adapterNews
        news.layoutManager = LinearLayoutManager(this)

        viewModel.getNews().observe(this, {
            adapterNews.submitList(it)
        })
    }

    private fun initSwipe() {
        swipe_news.onRefresh {
            swipe_news.isRefreshing = false
            viewModel.refresh()
        }
    }

    private fun initLoadings() {
        viewModel.loadingNews.observe(this, {
            if (it is Result.Success) {
                loading_news.isVisible(it.data)
            } else if (it is Result.Error) {
                loading_news.isVisible(false)
                toast(it.exception.message.toString())
            }
        })

        viewModel.loadingHighlights.observe(this, {
            if (it is Result.Success) {
                loading_highlights.isVisible(it.data)
            } else if (it is Result.Error) {
                loading_highlights.isVisible(false)
                toast(it.exception.message.toString())
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val favoriteMenu = menu?.add(1, MENU_FAVORITE, Menu.NONE, "Favorite")
        favoriteMenu?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        favoriteMenu?.setIcon(R.drawable.ic_heart)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == MENU_FAVORITE) {
            startDialog(FavoriteNewsDialog())
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val MENU_FAVORITE = 1
    }

    override fun onSharedClick(news: News) {
        ShareCompat.IntentBuilder.from(this)
            .setType("text/plain")
            .setChooserTitle(news.title)
            .setText(news.url)
            .setSubject(news.content)
            .startChooser()
    }

    override fun onFavoriteClick(news: News) {
        viewModel.favoriteNews(news)
    }

    override fun onOpenClick(news: News) {
        val bundle = Bundle()
        bundle.putSerializable("news", news)

        val viewNewsDialog = ViewNewsDialog()
        viewNewsDialog.arguments = bundle


        startDialog(viewNewsDialog)
    }
}