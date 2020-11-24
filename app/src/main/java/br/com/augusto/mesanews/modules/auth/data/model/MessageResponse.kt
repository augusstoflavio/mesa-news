package br.com.augusto.mesanews.modules.auth.data.model

import com.squareup.moshi.Json

data class MessageResponse (
    @field:Json(name = "message")
    var message: String
)