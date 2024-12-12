package com.example.loja.classes

class Produto(
    val id: Int,
    val nome: String,
    var preco: Int,
    var quantidade: Int
) {

    // Construtor secundário com valores padrão
    constructor(nome: String, preco: Int) : this(0, nome, preco, 0)

    // Atualiza o preço do próprio produto
    fun updatePrice(price: Int) {
        if (price >= 0) {
            this.preco = price
        } else {
            throw IllegalArgumentException("O preço não pode ser negativo.")
        }
    }

    // Verifica se o produto é um objeto padrão
    fun isDefault(): Boolean {
        return id == 0 && nome.isEmpty() && preco == 0 && quantidade == 0
    }
}
