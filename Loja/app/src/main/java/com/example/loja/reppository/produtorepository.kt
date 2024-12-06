package com.example.loja.repository

import com.example.loja.classes.Produto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProdutoRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val produtoCollection = firestore.collection("Produtos")


    suspend fun adicionarOuAtualizarProduto(produto: Produto): Boolean {
        return try {
            val produtoData = hashMapOf(
                "id" to produto.id,
                "nome" to produto.nome,
                "preco" to produto.preco,
                "quantidade" to produto.quantidade
            )
            produtoCollection.document("Produto_${produto.id}").set(produtoData).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


    suspend fun procuraProdutos(): List<Produto> {
        return try {
            val snapshot = produtoCollection.get().await()
            snapshot.documents.mapNotNull { document ->
                val id = document.getLong("id")?.toInt() ?: return@mapNotNull null
                val nome = document.getString("nome") ?: return@mapNotNull null
                val preco = document.getDouble("preco") ?: return@mapNotNull null
                val quantidade = document.getLong("quantidade")?.toInt() ?: return@mapNotNull null
                Produto(id, nome, preco, quantidade)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }


    suspend fun removerProduto(id: Int): Boolean {
        return try {
            produtoCollection.document("Produto_$id").delete().await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
