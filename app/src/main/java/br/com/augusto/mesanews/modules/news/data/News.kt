package br.com.augusto.mesanews.modules.news.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class News: RealmObject(), Serializable {
    @PrimaryKey
    var title: String? = null
    var description: String? = null
    var content: String? = null
    var author: String? = null
    var publishedAt: String? = null
    var highlight: Boolean? = null
    var url: String? = null
    var imageUrl: String? = null
    var favorite: Boolean = false

    override fun equals(other: Any?): Boolean {
        if (other !is News) return false
        val n = other as News?
        return n!!.title == title
    }

    override fun hashCode(): Int {
        var result = title?.hashCode() ?: 0
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (content?.hashCode() ?: 0)
        result = 31 * result + (author?.hashCode() ?: 0)
        result = 31 * result + (publishedAt?.hashCode() ?: 0)
        result = 31 * result + (highlight?.hashCode() ?: 0)
        result = 31 * result + (url?.hashCode() ?: 0)
        result = 31 * result + (imageUrl?.hashCode() ?: 0)
        result = 31 * result + favorite.hashCode()
        return result
    }
}