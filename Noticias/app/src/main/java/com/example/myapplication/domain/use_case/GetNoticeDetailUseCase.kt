package com.example.myapplication.domain.use_case

import com.example.myapplication.domain.model.NoticeDetail
import com.example.myapplication.domain.repository.NoticeRepository

class GetNoticeDetailUseCase (private val repository: NoticeRepository) {
    suspend operator fun invoke(noticeId: String): NoticeDetail {
        return repository.getNoticesDetail(noticeId)
    }
}