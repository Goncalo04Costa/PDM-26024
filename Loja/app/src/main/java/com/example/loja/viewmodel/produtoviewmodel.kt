package com.example.loja.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.loja.classes.Produto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ProdutosViewModel : ViewModel() {
    private val database = Firebase.firestore

    // Função para adicionar um produto ao Firestore
    suspend fun addProduct(product: Produto): Boolean {
        return try {
            // Adiciona o produto na coleção "produtos" no Firestore
            val productRef = database.collection("produtos").document()
            productRef.set(product)
            Log.d("ProdutosViewModel", "Produto adicionado com sucesso: ${product}")
            true
        } catch (e: Exception) {
            Log.d("ProdutosViewModel", "Erro ao adicionar o produto: ${e}")
            false
        }
    }

    // Função para buscar produtos do Firestore
    suspend fun fetchProducts(): List<Produto> {
        val resultProducts = mutableListOf<Produto>()
        return try {
            // Buscando produtos na coleção "produtos" do Firestore
            val querySnapshot = database.collection("produtos").get().await()
            for (document in querySnapshot) {
                val product = document.toObject(Produto::class.java)
                resultProducts.add(product)
            }
            Log.d("ProdutosViewModel", "Produtos buscados com sucesso.")
            resultProducts
        } catch (e: Exception) {
            Log.d("ProdutosViewModel", "Erro ao buscar produtos: ${e}")
            emptyList()
        }
    }
}
