package br.com.augusto.mesanews.app.ui.activity

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.augusto.mesanews.R
import kotlinx.android.synthetic.main.activity_toolbar.*
import kotlinx.android.synthetic.main.app_bar.*

abstract class ToolbarActivity : AppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(R.layout.activity_toolbar)
        setSupportActionBar(toolbar_app)
        toolbar_app.setNavigationIcon(R.drawable.ic_back)
        toolbar_app.setNavigationOnClickListener { v: View? -> onBackPressed() }

        val view = layoutInflater.inflate(layoutResID, content, false)
        content.addView(view)
    }
}