package com.example.noticias.app.domain.repository

import com.example.noticias.app.domain.model.Noticia
import com.example.noticias.app.domain.model.NoticiaDetalhada

interface  RepositorioNoticias{
    suspend fun getNoticias(): List<Noticia>
    suspend fun getNoticiasDetail(noticiaId: String): NoticiaDetalhada
}