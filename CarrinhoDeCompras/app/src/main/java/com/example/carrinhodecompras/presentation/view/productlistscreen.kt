package com.example.carrinhodecompras.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.carrinhodecompras.classes.Product
import com.example.carrinhodecompras.presentation.viewmodel.ProductViewModel

@Composable
fun ProductListScreen(
    productViewModel: ProductViewModel,
    onNavigateToCart: () -> Unit,
    onAddToCart: (Product) -> Unit
) {
    val state = productViewModel.productState.collectAsState().value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Using the correct typography styles
        Text("Produtos", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Displaying loading indicator when the data is being fetched
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (state.error != null) {
            // Displaying error message if there is one
            Text(state.error, color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            // Displaying list of products
            LazyColumn {
                items(state.products) { product ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .shadow(4.dp, shape = RectangleShape) // Using shadow instead of Card
                            .padding(16.dp)
                    ) {
                        Text(product.name, style = MaterialTheme.typography.titleLarge) // Corrected typography style
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Preço: R$${product.price}", style = MaterialTheme.typography.bodyLarge) // Corrected typography style
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { onAddToCart(product) }) {
                            Text("Adicionar ao Carrinho")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to navigate to the Cart
        Button(onClick = onNavigateToCart) {
            Text("Ver Carrinho")
        }
    }
}
