package com.example.goncalonews.app.data.remote.model

import com.example.goncalonews.app.domain.model.NoticiaDetail

data class NoticiaDetailDto(
    val id: String,            // ID da notícia
    val title: String,         // Título da notícia
    val description: String,   // Descrição da notícia (resumo ou introdução)
    val content: String?,      // Conteúdo completo da notícia (opcional)
    val author: String?,       // Autor da notícia (opcional)
    val image_url: String?,    // URL da imagem associada à notícia (opcional)
    val published_at: String,  // Data de publicação da notícia
    val source: String?        // Fonte da notícia (opcional)
)

fun NoticiaDetailDto.toNoticiaDetail(): NoticiaDetail {
    return NoticiaDetail(
        id = this.id,
        title = this.title,
        description = this.description,
        content = this.content,
        author = this.author,
        imageUrl = this.image_url,
        publishedAt = this.published_at,
        source = this.source
    )
}
