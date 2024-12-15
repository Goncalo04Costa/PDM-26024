package com.example.carrinhodecompras.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrinhodecompras.classes.Cart
import com.example.carrinhodecompras.classes.Product
import com.example.carrinhodecompras.repositorio.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CartState(
    val cart: Cart? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {
    private val _cartState = MutableStateFlow(CartState())
    val cartState: StateFlow<CartState> = _cartState

    fun loadCart(userId: String) = viewModelScope.launch {
        _cartState.value = _cartState.value.copy(isLoading = true, error = null)
        try {
            val cart = cartRepository.getCart(userId)
            _cartState.value = CartState(cart = cart)
        } catch (e: Exception) {
            _cartState.value = CartState(error = "Erro ao carregar carrinho.")
        }
    }

    fun addToCart(userId: String, product: Product, quantity: Int) = viewModelScope.launch {
        _cartState.value = _cartState.value.copy(isLoading = true, error = null)
        try {
            cartRepository.addToCart(userId, product, quantity)
            loadCart(userId) // Atualiza o carrinho após adicionar
        } catch (e: Exception) {
            _cartState.value = CartState(error = "Erro ao adicionar item ao carrinho.")
        }
    }

    fun clearCart(userId: String) = viewModelScope.launch {
        _cartState.value = _cartState.value.copy(isLoading = true, error = null)
        try {
            cartRepository.clearCart(userId)
            _cartState.value = CartState(cart = Cart(userId, emptyList()))
        } catch (e: Exception) {
            _cartState.value = CartState(error = "Erro ao limpar carrinho.")
        }
    }
}
