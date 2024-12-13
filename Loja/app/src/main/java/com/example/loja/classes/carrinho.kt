package com.example.loja.classes

import android.util.Log

class Carrinho(
    var idCarrinho: String? = "",
    var donoCarrinho: String? = "",
    var listaProdutos: MutableList<ProdutoCarrinho> = mutableListOf()
) {

    fun addProduct(prod: Produto, quantity: Int) {
        if (quantity <= 0) {
            Log.d("Carrinho", "Quantidade inválida: $quantity")
            return
        }

        val productExists = listaProdutos.find { it.produto == prod }
        if (productExists != null) {
            productExists.quantidade = (productExists.quantidade ?: 0) + quantity
            Log.d("Carrinho", "Quantidade atualizada: ${productExists.quantidade}")
        } else {
            val newProdutoCarrinho = ProdutoCarrinho(prod, quantity)
            listaProdutos.add(newProdutoCarrinho)
            Log.d("Carrinho", "Produto adicionado: ${prod.nome}, Quantidade: $quantity")
        }
    }

    fun removeProduct(prod: Produto) {
        val productExists = listaProdutos.find { it.produto == prod }
        if (productExists != null) {
            listaProdutos.remove(productExists)
            Log.d("Carrinho", "Produto removido: ${prod.nome}")
        } else {
            Log.d("Carrinho", "Produto não encontrado: ${prod.nome}")
        }
    }

    fun calculateTotal(): Int {
        return listaProdutos.sumOf { (it.produto?.preco ?: 0) * (it.quantidade ?: 0) }
    }
}
