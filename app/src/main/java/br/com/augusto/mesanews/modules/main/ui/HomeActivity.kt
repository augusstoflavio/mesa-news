package br.com.augusto.mesanews.modules.main.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.ui.adapter.OnClickItemAdapterListener
import br.com.augusto.mesanews.modules.news.data.News
import br.com.augusto.mesanews.modules.news.ui.activity.ShowNewsActivity
import br.com.augusto.mesanews.modules.news.ui.adapter.HighlightsAdapter
import br.com.augusto.mesanews.modules.news.ui.adapter.NewsAdapter
import br.com.augusto.mesanews.modules.news.ui.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity() {

    val viewModel: NewsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



        val adapter = NewsAdapter(
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
        news.adapter = adapter
        news.layoutManager = LinearLayoutManager(this)

        viewModel.news.observe(this, {
            adapter.submitList(it)
        })

        val highlightsAdapter = HighlightsAdapter()
        highlights.adapter = highlightsAdapter
        highlights.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        viewModel.highliht.observe(this, {
            highlightsAdapter.update(it)
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