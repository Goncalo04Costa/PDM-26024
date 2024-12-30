package com.example.goncalonews.app.data.remote.repository

import com.example.goncalonews.app.data.remote.api.NYtimesAPI
import com.example.goncalonews.app.data.remote.model.toNoticia
import com.example.goncalonews.app.data.remote.model.toNoticiaDetalhada
import com.example.goncalonews.app.domain.model.Noticia
import com.example.goncalonews.app.domain.model.NoticiaDetalhada
import com.example.goncalonews.app.domain.repository.NoticiaRepository

class NoticiaRepositoryImpl(private val api: NYtimesAPI) : NoticiaRepository {


    override suspend fun getNoticias(): List<Noticia> {
        return api.getNoticias().results.map { it.toNoticia() }  // Mapeia para o modelo de domínio Noticia
    }


    override suspend fun getNoticiasDetalhadas(noticiatitle: String): NoticiaDetalhada {
        // A função de conversão agora está correta
        return api.getNoticiaDetalhada(noticiatitle).docs.first().toNoticiaDetalhada()  // Mapeia para NoticiaDetalhada
    }
}
