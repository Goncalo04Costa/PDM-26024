package com.example.myapplication.domain.use_case

import com.example.myapplication.domain.model.Notice
import com.example.myapplication.domain.repository.NoticeRepository

class GetNoticiaUseCase(private val repository: NoticeRepository) {
    suspend operator  fun invoke(): List<Notice>{
        return  repository.getNotices()
    }
}