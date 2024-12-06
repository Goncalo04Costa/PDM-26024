package com.example.loja.repository

import com.example.loja.classes.Produto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CarrinhoRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val carrinhoCollection = firestore.collection("Carrinho")

    // Adicionar ou atualizar um produto no carrinho
    suspend fun adicionarOuAtualizarProduto(produto: Produto, quantidade: Int, userId: String) {
        if (quantidade <= 0) {
            removerProduto(produto, userId)
            return
        }

        val produtoMap = mapOf(
            "id" to produto.id,
            "nome" to produto.nome,
            "preco" to produto.preco,
            "quantidade" to quantidade
        )

        carrinhoCollection
            .document(userId)
            .collection("Itens")
            .document(produto.id.toString())
            .set(produtoMap)
            .await()
    }

    // Remover um produto do carrinho
    suspend fun removerProduto(produto: Produto, userId: String) {
        carrinhoCollection
            .document(userId)
            .collection("Itens")
            .document(produto.id.toString())
            .delete()
            .await()
    }


    suspend fun obterProdutosDoCarrinho(userId: String): List<Pair<Produto, Int>> {
        val documentos = carrinhoCollection
            .document(userId)
            .collection("Itens")
            .get()
            .await()

        return documentos.documents.mapNotNull { doc ->
            val produto = Produto(
                id = doc.getLong("id")?.toInt() ?: return@mapNotNull null,
                nome = doc.getString("nome") ?: return@mapNotNull null,
                preco = doc.getDouble("preco") ?: return@mapNotNull null,
                quantidade = 0 // Quantidade armazenada separadamente
            )
            val quantidade = doc.getLong("quantidade")?.toInt() ?: return@mapNotNull null
            Pair(produto, quantidade)
        }
    }


    suspend fun limparCarrinho(userId: String) {
        val itens = carrinhoCollection
            .document(userId)
            .collection("Itens")
            .get()
            .await()

        for (doc in itens.documents) {
            doc.reference.delete().await()
        }
    }
}
