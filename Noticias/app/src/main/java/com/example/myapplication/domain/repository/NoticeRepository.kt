package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Notice
import com.example.myapplication.domain.model.NoticeDetail

interface NoticeRepository{
   abstract val results: Any
   abstract val it: Any

   suspend fun getNotices(): List<Notice>
   suspend fun getNoticesDetail(noticeId: String): NoticeDetail

}