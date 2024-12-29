package com.example.goncalonews.app.domain.model

data class NoticiaDetail(
    val id: String,            // ID da notícia
    val title: String,         // Título da notícia
    val description: String,   // Descrição da notícia (resumo ou introdução)
    val content: String?,      // Conteúdo completo da notícia (opcional)
    val author: String?,       // Autor da notícia (opcional)
    val imageUrl: String?,     // URL da imagem associada à notícia (opcional)
    val publishedAt: String,   // Data de publicação da notícia
    val source: String?        // Fonte da notícia (opcional)
)
