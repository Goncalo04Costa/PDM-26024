package com.example.noticiasapi.app.data.remote.model

import com.example.noticiasapi.app.domain.model.Noticia

data class NoticiaDto(
    val id: String,
    val name: String,
    val author: String
) {
    fun toNoticia(): Noticia{
        return Noticia(id = id, name = name , author = author)
    }
}