package br.com.augusto.mesanews.modules.news.data

data class News (
    val title: String,
    val description: String,
    val content: String,
    val author: String,
    val publishedAt: String,
    val highlight: Boolean,
    val url: String,
    val imageUrl: String?
)