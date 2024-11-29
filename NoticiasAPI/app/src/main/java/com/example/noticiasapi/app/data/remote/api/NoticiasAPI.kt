package com.example.noticiasapi.app.data.remote.api

import com.example.noticiasapi.app.data.remote.model.NoticiaDetailDto
import com.example.noticiasapi.app.data.remote.model.NoticiaDto
import com.example.noticiasapi.app.domain.use_case.GetNoticiasUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object  RetrofitInstance{
    val api: NoticiasAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.thenewsapi.com/v1/news/\"")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NoticiasAPI::class.java)

    }
}

interface NoticiasAPI {
 @GET("v1/noticias")
 suspend fun getNoticias(): List<NoticiaDto>

 @GET("PJW3VC4qQmPAquZfz6pDwytcmun5F9b5SgLhlWZY")
 suspend fun  getNoticiasDetail(@Path("id") noticiaId: String): NoticiaDetailDto

}



