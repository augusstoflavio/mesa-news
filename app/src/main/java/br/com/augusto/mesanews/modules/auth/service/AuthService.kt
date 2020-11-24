package br.com.augusto.mesanews.modules.auth.service

import br.com.augusto.mesanews.modules.auth.data.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @FormUrlEncoded
    @POST("/v1/client/auth/signin")
    fun signin(@Field("email") user: String, @Field("password") email: String): Call<LoginResponse>

    @FormUrlEncoded
    @POST("/v1/client/auth/signup")
    fun signup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}