package com.example.noticias.app.data.remote.api

import com.example.noticias.app.data.remote.model.NoticiaDetalhadaDto
import com.example.noticias.app.data.remote.model.NoticiaDto

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object  RetrofitInstance {
    val api: NoticiasAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.thenewsapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NoticiasAPI:: class.java)
    }

}

interface NoticiasAPI  {
    @GET("v1/noticias")
    suspend fun getNoticias(): List<NoticiaDto>

    @GET("v1/noticias/{id}")
    suspend fun getNoticiasDetalhadas(@Path("id") noticiaId: String): NoticiaDetalhadaDto
}

