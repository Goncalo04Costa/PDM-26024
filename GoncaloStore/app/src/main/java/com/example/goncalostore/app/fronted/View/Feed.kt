package com.example.goncalostore.app.fronted.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.goncalostore.app.backend.models.Produto
import com.example.goncalostore.app.fronted.ViewModel.CarrinhoViewModel
import com.example.goncalostore.app.fronted.ViewModel.ProdutoViewModel
import com.example.goncalostore.app.ui.buttons.drawCircles
import com.example.goncalostore.app.ui.colors.DarkBlue
import com.example.goncalostore.app.ui.colors.LightBlue
import com.google.android.gms.analytics.ecommerce.Product




@Composable
fun FeedScreen(navController: NavHostController) {
    val productViewModel: ProdutoViewModel = viewModel()
    val products = productViewModel.produtos.collectAsState()
    val carrinhoViewModel: CarrinhoViewModel = viewModel()

    LaunchedEffect(Unit) {
        productViewModel.fetchProdutos()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DarkBlue)
    ) {
        // Draw circles at the top
        drawCircles(LightBlue, "Produtos", 35.sp)

        if (products.value.isEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = LightBlue
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(products.value) { product ->
                    ProductItem(
                        product = product,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        onAddToCart = { selectedProduct ->
                            // Call a suspend function in a coroutine
                            carrinhoViewModel.adicionarProdutoCoroutine(selectedProduct)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    product: Produto,
    modifier: Modifier = Modifier,
    onAddToCart: (Produto) -> Unit
) {
    Card(
        modifier = modifier
            .width(300.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Nome do produto
            Text(
                text = product.nome,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Descrição do produto
            Text(
                text = product.descricao,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Preço do produto
            Text(
                text = "Preço: R$ ${product.preco}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botão para adicionar ao carrinho
            Button(
                onClick = { onAddToCart(product) },
                colors = ButtonDefaults.buttonColors(containerColor = LightBlue)
            ) {
                Text(
                    text = "Adicionar ao Carrinho",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
