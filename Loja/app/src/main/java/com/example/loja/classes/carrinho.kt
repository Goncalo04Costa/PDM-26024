package com.example.loja.classes

class Carrinho {
    private val itens = mutableMapOf<Produto, Int>()

    // Adiciona produtos ao carrinho
    fun adicionarProduto(produto: Produto, quantidade: Int) {
        if (quantidade > 0 && quantidade <= produto.quantidade) {
            itens[produto] = (itens[produto] ?: 0) + quantidade
            produto.quantidade -= quantidade
            println("$quantidade unidades do produto '${produto.nome}' foram adicionadas ao carrinho.")
        } else {
            println("Erro: Quantidade inválida ou estoque insuficiente.")
        }
    }

    // Remove produtos do carrinho
    fun removerProduto(produto: Produto, quantidade: Int) {
        val qtdNoCarrinho = itens[produto] ?: 0
        if (quantidade > 0 && qtdNoCarrinho >= quantidade) {
            itens[produto] = qtdNoCarrinho - quantidade
            produto.quantidade += quantidade
            println("$quantidade unidades do produto '${produto.nome}' foram removidas do carrinho.")
            if (itens[produto] == 0) itens.remove(produto)
        } else {
            println("Erro: Quantidade inválida ou não presente no carrinho.")
        }
    }

    // Lista os produtos no carrinho
    fun listarProdutos() {
        if (itens.isEmpty()) {
            println("O carrinho está vazio.")
        } else {
            println("Produtos no carrinho:")
            itens.forEach { (produto, quantidade) ->
                println("${produto.nome} - Quantidade: $quantidade, Preço Total: R$ ${quantidade * produto.preco}")
            }
        }
    }

    // Calcula o total do carrinho
    fun calcularTotal(): Double {
        return itens.entries.sumOf { (produto, quantidade) -> produto.preco * quantidade }
    }
}