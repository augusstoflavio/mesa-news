package br.com.augusto.mesanews.app.data.resources

import com.squareup.moshi.Json

data class MetaResource (
    @field:Json(name = "current_page")
    var currentPage: Int?,
    @field:Json(name = "from")
    var from: Int?,
    @field:Json(name = "last_page")
    var lastPage: Int?,
    @field:Json(name = "path")
    var path: String?,
    @field:Json(name = "to")
    var to: Int?,
    @field:Json(name = "total")
    var total: Int?,
)