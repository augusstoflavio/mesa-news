package br.com.augusto.mesanews.modules.news.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.ui.activity.ToolbarActivity
import br.com.augusto.mesanews.app.ui.adapter.OnClickItemAdapterListener
import br.com.augusto.mesanews.modules.news.data.News
import br.com.augusto.mesanews.modules.news.ui.adapter.FavoriteNewsAdapter
import br.com.augusto.mesanews.modules.news.ui.viewModel.FavoriteNewsViewModel
import kotlinx.android.synthetic.main.activity_favorite_news.*
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteNewsActivity: ToolbarActivity() {

    val viewModel: FavoriteNewsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_news)
        setTitle("Favorite News")

        val adapterNews = FavoriteNewsAdapter(
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
        favorite_news.adapter = adapterNews
        favorite_news.layoutManager = LinearLayoutManager(this)

        viewModel.favoriteNews.observe(this, {
            adapterNews.update(it)
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