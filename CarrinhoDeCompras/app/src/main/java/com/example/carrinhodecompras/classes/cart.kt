package com.example.carrinhodecompras.classes

data class Cart(
    val userId: String,    // ID do usuário
    val items: List<CartItem> // Lista de itens no carrinho
) {
    fun getTotalPrice(): Double = items.sumOf { it.product.price * it.quantity }
}
