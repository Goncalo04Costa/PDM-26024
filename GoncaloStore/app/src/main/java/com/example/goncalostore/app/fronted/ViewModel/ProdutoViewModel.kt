package com.example.goncalostore.app.fronted.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.goncalostore.app.backend.models.Produto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ProdutoViewModel(application: Application) : AndroidViewModel(application) {
    private val database = Firebase.firestore
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val listProdutosFlow = MutableStateFlow<List<Produto>>(emptyList())
    val produtos: StateFlow<List<Produto>> get() = listProdutosFlow

    suspend fun addProduto(
        produtoToAdd: Produto,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ): Boolean {
        return try {
            // Validar os campos antes de guardar
            if (produtoToAdd.nome.isEmpty() || produtoToAdd.descricao.isEmpty()) {
                throw Exception("Nome e descrição são obrigatórios.")
            }

            val produtoId = UUID.randomUUID().toString()

            database.collection("Produtos")
                .document(produtoId)
                .set(produtoToAdd.toStore())
                .await()

            Log.d("ProdutoViewModel", "Produto resgistado com sucesso")
            onSuccess("Produto registrado com sucesso no Firestore")
            true
        } catch (ex: Exception) {
            Log.d("ProdutoViewModel", "Erro ao registar produto: ${ex.message}")
            onFailure("${ex.message}")
            false
        }
    }


    suspend fun fetchProdutos(): List<Produto> {
        return try {
            val resultFirebase = fetchProdutosFirebase(database)
            listProdutosFlow.value = resultFirebase
            resultFirebase
        } catch (ex: Exception) {
            Log.e("ProdutoViewModel", "Erro ao obter produtos: ${ex.message}")
            emptyList()
        }
    }



    private suspend fun fetchProdutosFirebase(databasereference: FirebaseFirestore): List<Produto> {
        return try {
            val query = databasereference.collection("Produtos")
                .get()
                .await()
            query.documents.mapNotNull { it.toObject(Produto::class.java) }
        } catch (ex: Exception) {
            Log.e("ProdutoViewModel", "Erro ao buscar produtos no Firebase: ${ex.message}")
            emptyList()
        }
    }


}
