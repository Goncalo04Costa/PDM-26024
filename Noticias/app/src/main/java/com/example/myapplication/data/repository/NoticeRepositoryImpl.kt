package com.example.myapplication.data.repository
import com.example.myapplication.data.remote.api.NoticiaApi
import com.example.myapplication.data.remote.model.NoticeDetailDto
import com.example.myapplication.domain.model.NoticeDetail
import com.example.myapplication.domain.repository.NoticeRepository


class NoticeRepositoryImpl(private val api: NoticiaApi) : NoticeRepository {
    override suspend fun getNotice(): List<Notice> {
        return api.getNoticia().map { it.toNotice() }

    }

    override suspend fun getNoticeDetail(coinId: String): NoticeDetail {
        return api.getNoticiaDetail(id).toNoticeDetail()

    }

}