package com.example.noticias.app.data.remote.model

import com.example.noticias.app.domain.model.Noticia

data class NoticiaDto(
    val id:String,
    val titulo:String,
    val autor : String
) {
    fun toNoticia(): Noticia {
        return Noticia(id = id, titulo = titulo, autor = autor)
    }
}