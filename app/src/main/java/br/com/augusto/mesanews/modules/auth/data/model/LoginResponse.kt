package br.com.augusto.mesanews.modules.auth.data.model

import com.squareup.moshi.Json

data class LoginResponse (
    @field:Json(name = "token")
    var token: String?,
)