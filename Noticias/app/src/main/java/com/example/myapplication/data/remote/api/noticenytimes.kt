package com.example.myapplication.data.remote.api

import com.example.myapplication.data.remote.model.NoticeDetailDto
import com.example.myapplication.domain.repository.NoticeRepository


interface NoticiaApi {
    @GET()
    suspend fun getNoticia(
        @Path("section") section: String = "home",
        @Query("api-key") apiKey: String = "suLQjOHdxSJpmqwpmgIMB0qa7wGGhrTh",
    ): NoticeRepository


    @GET
    suspend fun getNoticiaDetail(
        @Url noticiaUrl: String,
        @Query("api-key") apiKey: String = "sOoy4VCUGP2Ocbl6r0zVAihYGhZMSDz0"
    ): NoticeDetailDto
}