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
            .baseUrl("https://api.thenewsapi.com/v1/news/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NoticiasAPI:: class.java)
    }

}

interface NoticiasAPI  {
    @GET("top?api_token=PJW3VC4qQmPAquZfz6pDwytcmun5F9b5SgLhlWZY")
    suspend fun getNoticias(): List<NoticiaDto>

    @GET("top?api_token=PJW3VC4qQmPAquZfz6pDwytcmun5F9b5SgLhlWZY")
    suspend fun getNoticiasDetalhadas(@Path("id") noticiaId: String): NoticiaDetalhadaDto
}

