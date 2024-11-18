package com.example.myapplication.data.repository

import com.example.myapplication.data.remote.api.NoticiaApi
import com.example.myapplication.data.remote.model.NoticeDetailDto
import com.example.myapplication.domain.model.Notice
import com.example.myapplication.domain.model.NoticeDetail
import com.example.myapplication.domain.repository.NoticeRepository


class NoticiasRepositoryImpl(private val api: NoticiaApi) : NoticeRepository {
    override suspend fun getNoticias(): List<Notice> {
        return api.getNoticia().results.map { it.toNotice() }
    }
    override suspend fun getNoticiaDetail(noticiaUrl: String): NoticeDetail {
        val response = api.getNoticiaDetail(noticiaUrl)
        return response.toNoticeDetail()
    }
}