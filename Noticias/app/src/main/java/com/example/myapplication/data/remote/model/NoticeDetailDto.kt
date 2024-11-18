package com.example.myapplication.data.remote.model

import com.example.myapplication.domain.model.NoticeDetail

data class NoticeDetailDto(
    val id: String,
    val titulo: String,
    val conteudo: String
){

fun toNoticeDetail(): NoticeDetail {
    return NoticeDetail(
        id = id,
        titulo = titulo,
        conteudo = conteudo
    )
}}
