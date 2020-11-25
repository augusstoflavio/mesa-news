package br.com.augusto.mesanews.modules.main.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.helper.toast
import br.com.augusto.mesanews.app.ui.adapter.OnClickItemAdapterListener
import br.com.augusto.mesanews.modules.news.data.News
import br.com.augusto.mesanews.modules.news.ui.adapter.NewsAdapter
import br.com.augusto.mesanews.modules.news.ui.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(), OnClickItemAdapterListener<News> {

    val viewModel: NewsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val adapter = NewsAdapter(this)
        news.adapter = adapter
        news.layoutManager = LinearLayoutManager(this)

        viewModel.news.observe(this, {
            adapter.submitList(it)
        })
    }

    override fun clickItem(item: News) {
        ShareCompat.IntentBuilder.from(this)
            .setType("text/plain")
            .setChooserTitle(item.title)
            .setText(item.url)
            .setSubject(item.content)
            .startChooser()
    }
}