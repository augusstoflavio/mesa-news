package br.com.augusto.mesanews.app.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.core.view.isVisible

class WebViewProgressClient(
    val progressBar: ProgressBar
): WebViewClient() {

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        progressBar.isVisible = false
    }
}