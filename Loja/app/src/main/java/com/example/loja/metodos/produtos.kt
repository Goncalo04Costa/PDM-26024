package com.example.loja.metodos
import com.example.loja.classes.Produto


fun Produto.mostraproduto() {
    println("Produto: $nome")
    println("Preço: R€ $preco")
    println("Quantidade disponviel: $quantidade")
}


fun Produto.atualizapreco(novoPreco: Double) {
    if (novoPreco > 0) {
        preco = novoPreco
        println("Preço do produto '$nome' atualizado para R$ $preco")
    } else {
        println("Preço inválido. O valor deve ser positivo.")
    }
}


fun Produto.atualizarquantidade(qtd: Int) {
    if (qtd > 0) {
        quantidade += qtd
        println("Sucesso")
    } else {
        println("Erro")
    }
}


fun Produto.venda(qtd: Int) {
    if (qtd > 0 && qtd <= quantidade) {
        quantidade -= qtd
        println("Sucesso")
    } else {
        println("Erro")
    }
}
