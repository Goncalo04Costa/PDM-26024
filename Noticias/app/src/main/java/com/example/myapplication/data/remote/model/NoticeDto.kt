package com.example.myapplication.data.remote.model

import com.example.myapplication.domain.model.Notice

data class NoticeDto(
    val id: String,
    val titulo: String
){

    fun toNotice(): Notice {
        return Notice(
            id = id,
            titulo = titulo
        )
    }
}
