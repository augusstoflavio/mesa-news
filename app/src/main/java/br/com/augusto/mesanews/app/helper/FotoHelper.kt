package br.com.augusto.mesanews.app.helper

import android.widget.ImageView
import br.com.augusto.mesanews.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import java.util.*

class FotoHelper {

    fun load(url: String?, image: ImageView?, width: Int, height: Int) {
        var dir = url
        if (dir == null) {
            dir = ""
        }
        dir = getUrl(dir)
        getRequestCreator(dir, width, height).into(image)
    }

    private fun getRequestCreator(url: String, width: Int, height: Int): RequestCreator {
        return getPlaceholder(url)
            .resize(width, height)
            .centerCrop()
    }

    private fun getPlaceholder(url: String): RequestCreator {
        return Picasso.get()
            .load(url)
            .error(R.drawable.imagem_default_error)
            .placeholder(R.drawable.default_image)
    }

    private fun getUrl(url: String): String {
        var dir = url
        val split = dir.split("/".toRegex()).toTypedArray()
        if (!Arrays.asList(*split).contains("http:") &&
            !Arrays.asList(*split).contains("https:") &&
            !Arrays.asList(*split).contains("file:")
        ) {
            dir = "file://$dir"
        }
        return dir
    }
}