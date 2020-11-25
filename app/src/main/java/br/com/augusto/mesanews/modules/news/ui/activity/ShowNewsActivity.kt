package br.com.augusto.mesanews.modules.news.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ShareCompat
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val sharedFilter = menu?.add(1, MENU_SHARED, Menu.NONE, "Shared")
        sharedFilter!!.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        sharedFilter.setIcon(R.drawable.ic_baseline_share_24)
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        private const val MENU_SHARED = 1
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == MENU_SHARED) {
            sharedView(news!!)
        }
        return super.onOptionsItemSelected(item)
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