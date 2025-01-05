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
            // Validar os campos antes de salvar
            if (produtoToAdd.nome.isEmpty() || produtoToAdd.descricao.isEmpty()) {
                throw Exception("Nome e descrição são obrigatórios.")
            }

            val produtoId = UUID.randomUUID().toString()

            database.collection("Produtos")
                .document(produtoId)
                .set(produtoToAdd.toStore()) // Salva com o ID gerado manualmente
                .await() // Aguarda a operação assíncrona

            Log.d("ProdutoViewModel", "Produto registrado com sucesso no Firestore")
            onSuccess("Produto registrado com sucesso no Firestore")
            true
        } catch (ex: Exception) {
            Log.d("ProdutoViewModel", "Erro ao registrar produto: ${ex.message}")
            onFailure("${ex.message}")
            false
        }
    }

    // Função para buscar os produtos do Firebase
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

    // Função para atualizar o produto
    suspend fun updateProduto(produtoToUpdate: Produto): Boolean {
        return try {
            updateProdutoFirebase(produtoToUpdate)
        } catch (ex: Exception) {
            Log.e("ProdutoViewModel", "Erro ao atualizar produto: ${ex.message}")
            false
        }
    }

    // Função para excluir um produto
    suspend fun deleteProduto(produtoToDelete: Produto): Boolean {
        return try {
            deleteProdutoFirebase(produtoToDelete)
        } catch (ex: Exception) {
            Log.e("ProdutoViewModel", "Erro ao excluir produto: ${ex.message}")
            false
        }
    }

    // Métodos auxiliares para interação com o Firebase
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

    private suspend fun updateProdutoFirebase(produtoToUpdate: Produto): Boolean {
        return try {
            database.collection("Produtos")
                .document(produtoToUpdate.id.toString()) // Usando o ID como chave primária
                .set(produtoToUpdate, SetOptions.merge()) // Atualiza somente os campos fornecidos
                .await()
            true
        } catch (ex: Exception) {
            Log.e("ProdutoViewModel", "Erro ao atualizar produto no Firebase: ${ex.message}")
            false
        }
    }

    private suspend fun deleteProdutoFirebase(produtoToDelete: Produto): Boolean {
        return try {
            database.collection("Produtos")
                .document(produtoToDelete.id.toString()) // Usando o ID para localizar o produto
                .delete()
                .await()
            true
        } catch (ex: Exception) {
            Log.e("ProdutoViewModel", "Erro ao excluir produto no Firebase: ${ex.message}")
            false
        }
    }
}
