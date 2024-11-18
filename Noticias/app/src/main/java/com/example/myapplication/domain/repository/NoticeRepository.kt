package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Notice
import com.example.myapplication.domain.model.NoticeDetail

interface NoticeRepository{
   suspend fun getNotices(): List<Notice>
   suspend fun getNoticesDetail(noticeId: String): NoticeDetail
}