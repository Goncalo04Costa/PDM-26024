package com.example.goncalonews.app.domain.repository

import com.example.goncalonews.app.domain.model.Noticia
import com.example.goncalonews.app.domain.model.NoticiaDetail

interface NoticiaRepository {
    // Método para obter a lista de notícias
    suspend fun getNoticias(): List<Noticia>

    // Método para obter os detalhes de uma notícia específica, recebendo o id da notícia
    suspend fun getNoticiaDetail(id: String): NoticiaDetail
}
