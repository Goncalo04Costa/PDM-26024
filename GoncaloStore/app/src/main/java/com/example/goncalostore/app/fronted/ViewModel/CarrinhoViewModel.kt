package com.example.goncalostore.app.fronted.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.goncalostore.app.backend.models.Carrinho
import com.example.goncalostore.app.backend.models.Produto
import com.example.goncalostore.app.backend.models.Produtocarrinho
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class CarrinhoViewModel(application: Application) : AndroidViewModel(application) {
    private val database = Firebase.firestore
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _listCarrinhosFlow = MutableStateFlow<List<Carrinho>>(emptyList())
    val carrinhos: StateFlow<List<Carrinho>> get() = _listCarrinhosFlow

    private val _carrinhoAtivo = MutableStateFlow<Carrinho?>(null)
    val carrinhoAtivo: StateFlow<Carrinho?> get() = _carrinhoAtivo

    suspend fun addCarrinho(
        carrinhoToAdd: Carrinho,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ): Boolean {
        val currentUser = auth.currentUser
        val userEmail = currentUser?.email

        if (userEmail == null) {
            onFailure("Usuário não autenticado.")
            return false
        }

        return try {
            if (carrinhoToAdd.nome.isEmpty()) {
                throw Exception("O carrinho deve ter um nome.")
            }

            val carrinhoId = UUID.randomUUID().toString()
            val carrinhoComUsuario = carrinhoToAdd.copy(criadoPor = userEmail)

            database.collection("Carrinhos")
                .document(carrinhoId)
                .set(carrinhoComUsuario)
                .await()

            onSuccess("Carrinho registrado com sucesso no Firestore")
            true
        } catch (ex: Exception) {
            onFailure("Erro ao registrar carrinho: ${ex.message}")
            false
        }
    }

    suspend fun fetchCarrinhos(): List<Carrinho> {
        val currentUser = auth.currentUser
        val userEmail = currentUser?.email

        if (userEmail == null) {
            Log.e("CarrinhoViewModel", "Usuário não autenticado ao tentar buscar carrinhos.")
            return emptyList()
        }

        return try {
            val query = database.collection("Carrinhos")
                .whereEqualTo("criadoPor", userEmail)
                .get()
                .await()

            val carrinhos = query.documents.mapNotNull { it.toObject(Carrinho::class.java) }
            _listCarrinhosFlow.value = carrinhos
            carrinhos
        } catch (ex: Exception) {
            Log.e("CarrinhoViewModel", "Erro ao obter carrinhos: ${ex.message}")
            emptyList()
        }
    }

    suspend fun adicionarProdutoAoCarrinhoAtivo(produto: Produto) {
        val carrinhoAtual = _carrinhoAtivo.value

        if (carrinhoAtual == null) {
            Log.e("CarrinhoViewModel", "Carrinho ativo não encontrado.")
            return
        }

        try {
            // Obter a lista de produtos atual
            val novaListaProdutos = carrinhoAtual.produtos.toMutableList()
            Log.d("CarrinhoViewModel", "Lista de produtos antes: $novaListaProdutos")

            // Verificar se o produto já existe na lista
            val produtoExistente = novaListaProdutos.find { it.produto?.id == produto.id }
            if (produtoExistente != null) {
                // Atualizar a quantidade do produto existente
                produtoExistente.quantidade += 1
            } else {
                // Adicionar o novo produto na lista
                novaListaProdutos.add(
                    Produtocarrinho(
                        carrinhoId = carrinhoAtual.id,
                        produto = produto,
                        quantidade = 1
                    )
                )
            }

            // Atualizar o carrinho com a nova lista de produtos
            val carrinhoAtualizado = carrinhoAtual.copy(produtos = novaListaProdutos)
            Log.d("CarrinhoViewModel", "Carrinho atualizado: $carrinhoAtualizado")

            // Atualizar no Firebase
            database.collection("Carrinhos")
                .document(carrinhoAtual.id)
                .set(carrinhoAtualizado.toStore(), SetOptions.merge())
                .await()

            // Atualizar o estado local
            _carrinhoAtivo.value = carrinhoAtualizado
        } catch (ex: Exception) {
            Log.e("CarrinhoViewModel", "Erro ao adicionar produto ao carrinho ativo: ${ex.message}")
        }
    }

    suspend fun carregarOuCriarCarrinhoAtivo() {
        val currentUser = auth.currentUser
        val userEmail = currentUser?.email

        if (userEmail == null) {
            Log.e("CarrinhoViewModel", "Usuário não autenticado.")
            return
        }

        try {
            val query = database.collection("Carrinhos")
                .whereEqualTo("criadoPor", userEmail)
                .get()
                .await()

            val carrinho = query.documents.firstOrNull()?.toObject(Carrinho::class.java)
            if (carrinho != null) {
                _carrinhoAtivo.value = carrinho
            } else {
                val novoCarrinho = Carrinho(
                    id = UUID.randomUUID().toString(),
                    nome = "Meu Carrinho",
                    criadoPor = userEmail,
                    produtos = emptyList(),
                    compartilhadoCom = emptyList()
                )
                database.collection("Carrinhos").document(novoCarrinho.id).set(novoCarrinho).await()
                _carrinhoAtivo.value = novoCarrinho
            }
        } catch (ex: Exception) {
            Log.e("CarrinhoViewModel", "Erro ao carregar ou criar carrinho ativo: ${ex.message}")
        }
    }

    fun adicionarProdutoCoroutine(produto: Produto) {
        viewModelScope.launch {
            try {
                if (_carrinhoAtivo.value == null) {
                    carregarOuCriarCarrinhoAtivo()
                }
                adicionarProdutoAoCarrinhoAtivo(produto)
            } catch (e: Exception) {
                Log.e("CarrinhoViewModel", "Erro na coroutine de adicionar produto: ${e.message}")
            }
        }
    }
}
