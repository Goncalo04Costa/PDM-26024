package com.example.noticias.app.data.repository

import com.example.noticias.app.data.remote.api.NoticiasAPI
import com.example.noticias.app.data.remote.api.RetrofitInstance
import com.example.noticias.app.domain.model.Noticia
import com.example.noticias.app.domain.model.NoticiaDetalhada
import com.example.noticias.app.domain.repository.RepositorioNoticias

class RepositorioNoticiasImpl (private val api: NoticiasAPI) : RepositorioNoticias {
    override suspend fun getNoticias(): List<Noticia> {
        return  api.getNoticias().map { it.toNoticia() }
    }
    override suspend fun getNoticiasDetail(noticiaId: String): NoticiaDetalhada {
        return RetrofitInstance.api.getNoticiasDetalhadas(noticiaId).toNoticiaDetail()
}
}