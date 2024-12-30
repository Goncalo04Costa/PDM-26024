package com.example.goncalonews.app.data.remote.model

import com.example.goncalonews.app.domain.model.Noticia

data class NoticiaDto(
    val title: String,
    val web_url: String,
    val published_date: String
)

// Função de extensão para converter NoticiaDto para Noticia
fun NoticiaDto.toNoticia(): Noticia {
    return Noticia(
        title = this.title,
        web_url = this.web_url,
        published_date = this.published_date
    )
}
