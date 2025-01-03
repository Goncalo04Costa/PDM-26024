package com.example.goncalonews.app.domain.repository

import com.example.goncalonews.app.domain.model.Noticia
import com.example.goncalonews.app.domain.model.NoticiaDetalhada

interface noticiarepository {
    suspend fun getNoticias(): List<Noticia>
    suspend fun getNoticiasDetalhadas(noticiatittle: String): NoticiaDetalhada
}