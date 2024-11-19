package com.example.noticiasapi.app.data.remote.repository

import com.example.noticiasapi.app.data.remote.api.NoticiasAPI
import com.example.noticiasapi.app.domain.model.Noticia
import com.example.noticiasapi.app.domain.model.NoticiaDetail
import com.example.noticiasapi.app.domain.repository.NoticiaRepository

class NoticiaRepositoryImpl(private val api: NoticiasAPI) : NoticiaRepository {

    override suspend fun getNoticias(): List<Noticia> {
        return api.getNoticias().data.map { it.toNoticia() }
    }

    override suspend fun getNoticiaDetail(noticiaId: String): NoticiaDetail {
        // Chama o API e mapeia os dados para NoticiaDetail
        val response = api.getNoticiaDetail(noticiaId) // ResponseApiDetail
        return response.data.toNoticiaDetail() // Transforma o DTO para o modelo de domínio
    }
}
