package br.com.augusto.mesanews.modules.news.data

import com.squareup.moshi.Json

data class NewsResource (
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "content")
    val content: String,
    @field:Json(name = "author")
    val author: String,
    @field:Json(name = "published_at")
    val publishedAt: String,
    @field:Json(name = "highlight")
    val highlight: Boolean,
    @field:Json(name = "url")
    val url: String,
    @field:Json(name = "imageUrl")
    val imageUrl: String
)