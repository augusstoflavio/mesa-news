package br.com.augusto.mesanews.app.data.resources

import com.squareup.moshi.Json

data class LinksResource (
    @field:Json(name = "first")
    var first: String?,
    @field:Json(name = "last")
    var last: String?,
    @field:Json(name = "prev")
    var prev: String?,
    @field:Json(name = "next")
    var next: String?
)