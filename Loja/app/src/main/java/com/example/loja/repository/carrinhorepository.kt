package com.example.loja.repository

import android.util.Log
import com.example.loja.classes.Carrinho
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class CarrinhoRepository(private val databaseReference: FirebaseFirestore) {

    // Adicionar novo carrinho
    suspend fun adicionarCarrinho(carrinhoToAdd: Carrinho): Boolean {
        return try {
            carrinhoToAdd.idCarrinho?.let {
                databaseReference.collection("Carrinhos")
                    .document(it)
                    .set(carrinhoToAdd)
                    .await()
            }
            true
        } catch (e: Exception) {
            Log.d("CarrinhoRepository", "Erro ao adicionar o carrinho: ${e.message}")
            false
        }
    }

    // Buscar todos os carrinhos
    suspend fun buscarCarrinhos(): List<Carrinho> {
        return try {
            val carrinhosSnapshot = databaseReference.collection("Carrinhos")
                .get()
                .await()
            carrinhosSnapshot.documents.mapNotNull { it.toObject<Carrinho>() }
        } catch (e: Exception) {
            Log.d("CarrinhoRepository", "Erro ao buscar carrinhos: ${e.message}")
            emptyList()
        }
    }

    // Atualizar um carrinho
    suspend fun atualizarCarrinho(carrinhoToUpdate: Carrinho): Boolean {
        return try {
            carrinhoToUpdate.idCarrinho?.let {
                databaseReference.collection("Carrinhos")
                    .document(it)
                    .set(carrinhoToUpdate)
                    .await()
            }
            true
        } catch (e: Exception) {
            Log.d("CarrinhoRepository", "Erro ao atualizar o carrinho: ${e.message}")
            false
        }
    }

    // Remover um carrinho
    suspend fun removerCarrinho(idCarrinho: String): Boolean {
        return try {
            databaseReference.collection("Carrinhos")
                .document(idCarrinho)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            Log.d("CarrinhoRepository", "Erro ao remover o carrinho: ${e.message}")
            false
        }
    }
}
