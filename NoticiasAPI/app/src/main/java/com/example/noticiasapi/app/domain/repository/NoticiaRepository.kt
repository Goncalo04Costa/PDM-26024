package com.example.noticiasapi.app.domain.repository

import com.example.noticiasapi.app.domain.model.Noticia
import com.example.noticiasapi.app.domain.model.NoticiaDetail

interface NoticiaRepository{
    suspend fun getNoticias(): List<Noticia>
    suspend fun getNoticiaDetail(noticiaId: String): NoticiaDetail
}