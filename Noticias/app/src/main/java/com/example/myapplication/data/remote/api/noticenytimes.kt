package com.example.myapplication.data.remote.api

import com.example.myapplication.data.remote.model.NoticeDetailDto
import com.example.myapplication.domain.model.Notice
import com.example.myapplication.domain.repository.NoticeRepository

interface NoticeApi{
    @GET("v1/Notice")
    suspend fun getNotices(): List<Notice>

    @GET("v1/Notice/{id}")
    suspend fun  getNoticeDetail(@Path("id")noticeId:String) : NoticeDetailDto
}

interface NoticiaApi {
    @GET("{section}.json")
    suspend fun getNoticia(
        @Path("section") section: String = "home",
        @Query("api-key") apiKey: String = "sOoy4VCUGP2Ocbl6r0zVAihYGhZMSDz0",
    ): NoticeRepository
}