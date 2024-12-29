package com.example.goncalonews.app.data.remote.model

import com.example.goncalonews.app.domain.model.Noticia


data class NoticiaDto(
    val id: String,           // ID da notícia
    val title: String,        // Título da notícia
    val description: String,  // Descrição da notícia
    val image_url: String?,   // URL da imagem associada à notícia (opcional)
    val published_at: String  // Data de publicação da notícia
)

fun NoticiaDto.toNoticia(): Noticia {
    return Noticia(
        id = this.id,
        title = this.title,
        description = this.description,
        imageUrl = this.image_url,
        publishedAt = this.published_at
    )
}
