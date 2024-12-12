package com.example.loja.repository

import android.util.Log
import com.example.loja.classes.Produto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class ProdutoRepository(private val databaseReference: FirebaseFirestore) {


    suspend fun novoproduto(produto: Produto): Boolean {
        return try {
            databaseReference.collection("Produtos")
                .document(produto.id.toString())
                .set(produto)
                .await()
            true
        } catch (e: Exception) {
            Log.d("ProdutoRepository", "Erro ao adicionar o produto: ${e.message}")
            false
        }
    }


    suspend fun procuraproduto(): List<Produto> {
        return try {
            val produtosSnapshot = databaseReference.collection("Produtos")
                .get()
                .await()
            produtosSnapshot.documents.mapNotNull { it.toObject<Produto>() }
        } catch (e: Exception) {
            Log.d("ProdutoRepository", "Erro ao buscar produtos: ${e.message}")
            emptyList()
        }
    }


    suspend fun atualizaproduto(produto: Produto): Boolean {
        return try {
            databaseReference.collection("Produtos")
                .document(produto.id.toString())
                .set(produto)
                .await()
            true
        } catch (e: Exception) {
            Log.d("ProdutoRepository", "Erro ao atualizar o produto: ${e.message}")
            false
        }
    }


    suspend fun remove(idProduto: Int): Boolean {
        return try {
            databaseReference.collection("Produtos")
                .document(idProduto.toString())
                .delete()
                .await()
            true
        } catch (e: Exception) {
            Log.d("ProdutoRepository", "Erro ao remover o produto: ${e.message}")
            false
        }
    }
}
