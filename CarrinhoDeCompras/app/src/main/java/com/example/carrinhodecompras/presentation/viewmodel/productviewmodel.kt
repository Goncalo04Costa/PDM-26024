package com.example.carrinhodecompras.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrinhodecompras.classes.Product
import com.example.carrinhodecompras.repositorio.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ProductState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _productState = MutableStateFlow(ProductState())
    val productState: StateFlow<ProductState> = _productState

    fun loadProducts() = viewModelScope.launch {
        _productState.value = _productState.value.copy(isLoading = true, error = null)
        try {
            val products = productRepository.getProducts()
            _productState.value = ProductState(products = products)
        } catch (e: Exception) {
            _productState.value = ProductState(error = "Erro ao carregar produtos.")
        }
    }
}
