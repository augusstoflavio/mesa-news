package br.com.augusto.mesanews.modules.main.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.data.Result
import br.com.augusto.mesanews.app.helper.toast
import br.com.augusto.mesanews.app.ui.adapter.OnClickItemAdapterListener
import br.com.augusto.mesanews.modules.news.data.News
import br.com.augusto.mesanews.modules.news.ui.activity.ShowNewsActivity
import br.com.augusto.mesanews.modules.news.ui.adapter.HighlightsAdapter
import br.com.augusto.mesanews.modules.news.ui.adapter.NewsAdapter
import br.com.augusto.mesanews.modules.news.ui.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity() {

    private lateinit var adapterNews: NewsAdapter
    val viewModel: NewsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



        adapterNews = NewsAdapter(
            object : OnClickItemAdapterListener<News> {
                override fun clickItem(item: News) {
                    sharedNews(item)
                }
            },
            object : OnClickItemAdapterListener<News> {
                override fun clickItem(item: News) {
                    viewNews(item)
                }
            },
            object : OnClickItemAdapterListener<News> {
                override fun clickItem(item: News) {
                    favoriteNews(item)
                }
            }
        )
        news.adapter = adapterNews
        news.layoutManager = LinearLayoutManager(this)

        viewModel.news.observe(this, {
            adapterNews.submitList(it)
        })

        val highlightsAdapter = HighlightsAdapter()
        highlights.adapter = highlightsAdapter
        highlights.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        viewModel.highliht.observe(this, {
            highlightsAdapter.update(it)
        })

        viewModel.newsFavoriteUpdateResult.observe(this, {
            if (it == null) {
                return@observe
            }

            if (it is Result.Success) {
                adapterNews.changeFavState(it.data)
            } else if(it is Result.Error) {
                toast(it.exception.message!!)
            }

            viewModel.newsFavoriteUpdateResult.value = null
        })
    }

    private fun favoriteNews(item: News) {
        viewModel.favoriteNews(item)
    }

    fun sharedNews(news: News) {
        ShareCompat.IntentBuilder.from(this)
            .setType("text/plain")
            .setChooserTitle(news.title)
            .setText(news.url)
            .setSubject(news.content)
            .startChooser()
    }

    fun viewNews(news: News) {
        val intent = Intent(applicationContext, ShowNewsActivity::class.java)
        intent.putExtra("news", news)
        startActivity(intent)
    }
}