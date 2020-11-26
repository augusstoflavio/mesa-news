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
}