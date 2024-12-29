package com.example.carrinhodecompras.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.carrinhodecompras.classes.CartItem
import com.example.carrinhodecompras.presentation.viewmodel.CartViewModel

@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    onCheckout: () -> Unit
) {
    val state = cartViewModel.cartState.collectAsState().value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Carrinho", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.error != null -> {
                // Exibir o erro, se houver
                Text(state.error, color = Color.Red, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            else -> {
                // Verificar se o carrinho está vazio
                state.cart?.let { cart ->
                    if (cart.items.isEmpty()) {
                        // Mensagem se o carrinho estiver vazio
                        Text("Seu carrinho está vazio.", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else {
                        // Exibir itens do carrinho quando disponível
                        LazyColumn {
                            items(cart.items) { cartItem ->
                                CartItemView(cartItem = cartItem)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Exibir o preço total do carrinho
                        Text("Total: R$${cart.getTotalPrice()}", style = MaterialTheme.typography.bodyLarge)

                        Spacer(modifier = Modifier.height(8.dp))

                        // Botão para finalizar a compra
                        Button(onClick = onCheckout, modifier = Modifier.fillMaxWidth()) {
                            Text("Finalizar Compra")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Botão para limpar o carrinho
                        Button(
                            onClick = { cartViewModel.clearCart(cart.userId) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Limpar Carrinho")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemView(cartItem: CartItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(elevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(cartItem.product.name, style = MaterialTheme.typography.bodyLarge)
                Text("Quantidade: ${cartItem.quantity}", style = MaterialTheme.typography.bodyMedium)
            }
            Text("Subtotal: R$${cartItem.product.price multiply cartItem.quantity}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
