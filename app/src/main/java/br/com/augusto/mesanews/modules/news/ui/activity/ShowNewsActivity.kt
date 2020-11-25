package br.com.augusto.mesanews.modules.news.ui.activity

import android.os.Bundle
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.ui.WebViewProgressClient
import br.com.augusto.mesanews.app.ui.activity.ToolbarActivity
import br.com.augusto.mesanews.modules.news.data.News
import kotlinx.android.synthetic.main.activity_show_news.*

class ShowNewsActivity: ToolbarActivity() {

    private var news: News? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_news)


        val extras = intent.extras
        if (extras == null) {
            finish()
            return
        }

        news = extras.getSerializable("news") as News?
        if (news == null) {
            finish()
            return
        }


        setTitle(news!!.title)
        web_view.settings.javaScriptEnabled = true
        web_view.webViewClient = WebViewProgressClient(progress)
        web_view.loadUrl(news!!.url)
    }

}