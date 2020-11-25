package br.com.augusto.mesanews.modules.main.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.helper.toast
import br.com.augusto.mesanews.modules.news.ui.adapter.NewsAdapter
import br.com.augusto.mesanews.modules.news.ui.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    val viewModel: NewsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel.news.observe(this, {
            toast(it.size.toString())
        })

        val adapter = NewsAdapter()
        news.adapter = adapter
        news.layoutManager = LinearLayoutManager(this)

        viewModel.news.observe(this, {
            adapter.update(it)
        })
    }
}