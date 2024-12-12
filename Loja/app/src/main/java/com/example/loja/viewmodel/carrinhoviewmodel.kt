package com.example.loja.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loja.classes.Carrinho
import com.example.loja.repository.CarrinhoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarrinhosViewModel(private val carrinhoRepository: CarrinhoRepository) : ViewModel() {
    private val _carrinhos = MutableStateFlow<List<Carrinho>>(emptyList())
    val carrinhos: StateFlow<List<Carrinho>> = _carrinhos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchCarrinhos() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val fetchedCarrinhos = carrinhoRepository.buscarCarrinhos()
                _carrinhos.value = fetchedCarrinhos // Update the state flow
            } catch (e: Exception) {
                _error.value = "Erro ao buscar carrinhos: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun saveCarrinho(carrinho: Carrinho) {
        viewModelScope.launch {
            try {
                carrinhoRepository.adicionarCarrinho(carrinho)
                fetchCarrinhos() // Refresh the list
            } catch (e: Exception) {
                _error.value = "Erro ao salvar carrinho: ${e.message}"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
