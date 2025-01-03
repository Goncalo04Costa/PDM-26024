package com.example.goncalonews.app.data.remote.model

import com.example.goncalonews.app.domain.model.Noticia

data class NoticiaDto(
    val title: String,
    val url: String,
    val published_date: String
)


fun NoticiaDto.toNoticia(): Noticia {
    return Noticia(
        title = this.title,
        url = this.url,
        published_date = this.published_date
    )
}
