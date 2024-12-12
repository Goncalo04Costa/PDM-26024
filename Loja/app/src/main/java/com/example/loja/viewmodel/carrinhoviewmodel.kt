package com.example.loja.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.loja.classes.Carrinho
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CarrinhosViewModel: ViewModel() {
    private val database = Firebase.firestore

    // Função suspensa para buscar todos os carrinhos
    suspend fun fetchCarrinhos(): List<Carrinho> {
        return try {
            val resultCarrinhos = buscarCarrinhos()
            Log.d("CarrinhosViewModel", "Carrinhos fetched: $resultCarrinhos")
            resultCarrinhos
        } catch (e: Exception) {
            Log.d("CarrinhosViewModel", "Erro: ${e.message}")
            emptyList()
        }
    }

    // Função para buscar os carrinhos do Firestore
    private suspend fun buscarCarrinhos(): List<Carrinho> {
        return try {
            // A consulta para buscar a coleção "carrinhos" do Firestore
            val snapshot = database.collection("carrinhos").get().await()
            val carrinhos = snapshot.documents.mapNotNull { document ->
                // Mapeia cada documento para um objeto Carrinho
                document.toObject(Carrinho::class.java)
            }
            carrinhos
        } catch (e: Exception) {
            Log.d("CarrinhosViewModel", "Erro ao buscar carrinhos: ${e.message}")
            emptyList()
        }
    }

    // Função para buscar um carrinho específico
    suspend fun buscarCarrinho(carrinhoId: String): Carrinho? {
        return try {
            val snapshot = database.collection("carrinhos").document(carrinhoId).get().await()
            snapshot.toObject(Carrinho::class.java)
        } catch (e: Exception) {
            Log.d("CarrinhosViewModel", "Erro ao buscar carrinho: ${e.message}")
            null
        }
    }

    suspend fun salvarCarrinho(carrinho: Carrinho) {
        try {
            // Salva o carrinho no Firestore com o id gerado
            carrinho.idCarrinho?.let {
                database.collection("carrinhos")
                    .document(it) // Usando o ID do carrinho para garantir que seja atualizado se já existir
                    .set(carrinho)
                    .await()
            }
            Log.d("CarrinhosViewModel", "Carrinho salvo com sucesso: ${carrinho.idCarrinho}")
        } catch (e: Exception) {
            Log.d("CarrinhosViewModel", "Erro ao salvar carrinho: ${e.message}")
        }
    }
}
