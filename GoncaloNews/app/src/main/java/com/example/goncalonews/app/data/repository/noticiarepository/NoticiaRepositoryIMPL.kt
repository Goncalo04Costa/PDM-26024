package com.example.goncalonews.app.data.repository.noticiarepository

import com.example.goncalonews.app.data.remote.api.SportmonksApi
import com.example.goncalonews.app.data.remote.model.toNoticia
import com.example.goncalonews.app.data.remote.model.toNoticiaDetail
import com.example.goncalonews.app.domain.model.Noticia
import com.example.goncalonews.app.domain.model.NoticiaDetail
import com.example.goncalonews.app.domain.repository.NoticiaRepository

class NoticiaRepositoryImpl(private val api: SportmonksApi) : NoticiaRepository {

    // Método para buscar a lista de notícias
    override suspend fun getNoticias(): List<Noticia> {
        // Fazendo a requisição para obter as notícias e mapeando os dados para o modelo de domínio
        return api.getNoticias().map { it.toNoticia() }
    }

    // Método para buscar os detalhes de uma notícia específica
    override suspend fun getNoticiaDetail(id: String): NoticiaDetail {
        // Fazendo a requisição para obter os detalhes de uma notícia e mapeando para o modelo de domínio
        val noticiaDetailDto = api.getNoticiaDetail(id)
        return noticiaDetailDto.toNoticiaDetail()
    }
}
