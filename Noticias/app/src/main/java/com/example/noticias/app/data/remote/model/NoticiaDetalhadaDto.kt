package com.example.noticias.app.data.remote.model

import com.example.noticias.app.domain.model.NoticiaDetalhada

data class  NoticiaDetalhadaDto(
    val id:String,
    val titulo:String,
    val descricao: String,
) {
    fun toNoticiaDetail(): NoticiaDetalhada {
        return NoticiaDetalhada(id = id, titulo = titulo, descricao = descricao)
    }
}