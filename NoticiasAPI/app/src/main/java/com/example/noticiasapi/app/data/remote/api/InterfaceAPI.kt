package com.example.noticiasapi.app.data.remote.api

import com.example.noticiasapi.app.data.remote.model.ResponseAPI
import com.example.noticiasapi.app.data.remote.model.ResponseApiDetail
import retrofit2.http.GET
import retrofit2.http.Query

interface NoticiasAPI {
    //Obter todas as noticias que estão no top de noticias
    @GET("top?api_token=suLQjOHdxSJpmqwpmgIMB0qa7wGGhrTh")
    suspend fun getNoticias(): ResponseAPI


    //Obter novamente todas as noticias que estão no top, mas agora cria uma lista para noticias detalhadas
    @GET("top?api_token=suLQjOHdxSJpmqwpmgIMB0qa7wGGhrTh")
    suspend fun getNoticiaDetail(): ResponseApiDetail



}