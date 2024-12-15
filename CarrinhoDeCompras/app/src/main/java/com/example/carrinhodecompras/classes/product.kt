package com.example.carrinhodecompras.classes

data class Product(
    val id: String,        // ID do produto (Firebase Document ID)
    val name: String,      // Nome do produto
    val price: Double,     // Preço do produto
    val description: String // Descrição
)
