package com.example.goncalonews.app.domain.model

// Classe Noticia para exibir uma lista resumida de notícias
data class Noticia(
    val id: String,           // ID única para a notícia
    val title: String,        // Título da notícia
    val description: String,  // Descrição curta da notícia
    val imageUrl: String?,    // URL da imagem associada à notícia, opcional
    val publishedAt: String   // Data de publicação da notícia
)

