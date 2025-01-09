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
import com.example.goncalostore.app.backend.models.Carrinho
import com.example.goncalostore.app.backend.models.Produto
import com.example.goncalostore.app.fronted.ViewModel.CarrinhoViewModel
import com.example.goncalostore.app.fronted.ViewModel.ProdutoViewModel
import com.example.goncalostore.app.ui.buttons.drawCircles
import com.example.goncalostore.app.ui.colors.DarkBlue
import com.example.goncalostore.app.ui.colors.LightBlue
import com.google.android.gms.analytics.ecommerce.Product




@Composable
fun MostraCarrinhosScreen(navController: NavHostController) {
    val carrinhoViewModel: CarrinhoViewModel = viewModel()
    val carrinhos = carrinhoViewModel.carrinhos.collectAsState()

    LaunchedEffect(Unit) {
        carrinhoViewModel.fetchCarrinhos()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DarkBlue)
    ) {

        drawCircles(LightBlue, " Carrinhos", 35.sp)

        if (carrinhos.value.isEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = LightBlue
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(carrinhos.value) { carrinho ->
                    CarrinhoItemSimplificado(
                        carrinho = carrinho,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CarrinhoItemSimplificado(
    carrinho: Carrinho,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Nome do carrinho
            Text(
                text = carrinho.criadoPor,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Quantidade de produtos no carrinho
            Text(
                text = "Produtos: ${carrinho.produtos.size}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
