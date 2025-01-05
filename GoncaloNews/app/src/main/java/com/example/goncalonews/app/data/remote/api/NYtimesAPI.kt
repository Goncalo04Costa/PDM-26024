package com.example.goncalonews.app.data.remote.api

import com.example.goncalonews.app.data.remote.model.NoticiaDetailDto
import com.example.goncalonews.app.data.remote.model.NoticiaDto
import com.example.goncalonews.app.data.remote.model.resultnoticia
import com.example.goncalonews.app.data.remote.model.resultnoticiadetalhada
import com.example.goncalonews.app.domain.model.Noticia
import com.example.goncalonews.app.domain.model.NoticiaDetalhada
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NYtimesAPI {

    @GET("topstories/v2/home.json")
    suspend fun getNoticias(
        @Query("api-key") apiKey: String = "suLQjOHdxSJpmqwpmgIMB0qa7wGGhrTh"
    ): resultnoticia

    @GET("search/v2/articlesearch.json")
    suspend fun getNoticiaDetalhada(
        @Query("fq") fq: String,
        @Query("api-key") apiKey: String = "suLQjOHdxSJpmqwpmgIMB0qa7wGGhrTh"
    ): resultnoticiadetalhada
}
