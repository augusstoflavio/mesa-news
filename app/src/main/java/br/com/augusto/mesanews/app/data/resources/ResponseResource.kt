package br.com.augusto.mesanews.app.data.resources

import com.squareup.moshi.Json

class ResponseResource<T> (
    @field:Json(name = "data")
    var data: T,
    @field:Json(name = "links")
    var links: LinksResource?,
    @field:Json(name = "meta")
    var meta: MetaResource?,
)