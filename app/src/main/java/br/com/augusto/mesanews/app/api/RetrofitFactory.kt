package br.com.augusto.mesanews.app.api

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitFactory {
    companion object {

        fun withoutAuthentication(): Retrofit {
            val retrofit = builder()
                .build()

            return retrofit
        }

        fun withAuthentication(token: String): Retrofit {
            val client = OkHttpClient.Builder().addInterceptor { chain ->
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(newRequest)
            }.build()

            val retrofit = builder()
                .client(client)
                .build()

            return retrofit
        }

        private fun builder(): Retrofit.Builder {
            val moshi = Moshi.Builder().build()

            return Retrofit.Builder()
                .baseUrl("https://mesa-news-api.herokuapp.com")
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        }
    }
}