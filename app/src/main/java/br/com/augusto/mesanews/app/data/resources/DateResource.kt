package br.com.augusto.mesanews.app.data.resources

import com.squareup.moshi.Json

data class DateResource (
    @field:Json(name = "date")
    var date: String,
    @field:Json(name = "timezone")
    var timezone: String
)