package com.example.loja.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.loja.classes.Produto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ProdutosViewModel : ViewModel() {
    private val database = Firebase.firestore
    val errorMessage: MutableState<String?> = mutableStateOf(null)

    // Função para adicionar um produto ao Firestore
    suspend fun addProduct(product: Produto): Boolean {
        return try {
            val productRef = database.collection("produtos").document()
            productRef.set(product)
            Log.d("ProdutosViewModel", "Produto adicionado com sucesso: $product")
            true
        } catch (e: Exception) {
            Log.d("ProdutosViewModel", "Erro ao adicionar o produto: $e")
            errorMessage.value = "Erro ao adicionar o produto. Tente novamente."
            false
        }
    }

    // Função para buscar produtos do Firestore
    suspend fun fetchProducts(): List<Produto> {
        val resultProducts = mutableListOf<Produto>()
        return try {
            val querySnapshot = database.collection("produtos").get().await()
            for (document in querySnapshot) {
                val product = document.toObject(Produto::class.java)
                resultProducts.add(product)
            }
            Log.d("ProdutosViewModel", "Produtos buscados com sucesso.")
            resultProducts
        } catch (e: Exception) {
            Log.d("ProdutosViewModel", "Erro ao buscar produtos: $e")
            errorMessage.value = "Erro ao carregar os produtos. Tente novamente."
            emptyList()
        }
    }
}
