package com.example.loja.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.loja.UIElements.CreateBottomButtons
import com.example.loja.viewmodel.ProdutosViewModel
import com.example.loja.viewmodel.MenuUtilizadorViewModel
import com.example.loja.UIElements.CreateText
import com.example.loja.classes.Produto
import kotlinx.coroutines.launch

@Composable
fun MenuProdutos(viewModelProdutos: ProdutosViewModel,
                 viewModelUtilizador: MenuUtilizadorViewModel,
                 navController: NavController,
) {
    val listProducts = remember { mutableStateOf(emptyList<Produto>()) }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = viewModelProdutos.errorMessage.value

    LaunchedEffect(Unit) {
        try {
            val products = viewModelProdutos.fetchProducts()
            listProducts.value = products
            isLoading.value = false
        } catch (e: Exception) {
            Log.d("ProdutosScreen", "Erro: $e")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (isLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (listProducts.value.isEmpty()) {
                        item {
                            CreateText(
                                "Não existem produtos",
                                modifier = Modifier.fillMaxSize(),
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                size = 32.sp
                            )
                        }
                    } else {
                        items(listProducts.value) { item ->
                            ProductItemBox(
                                nome = item.nome,
                                preco = item.preco.toString(),
                                onClick = {
                                    // Navigate to product details or edit screen
                                    navController.navigate("ProductDetails/${item.id}")
                                }
                            )
                        }
                    }
                }

                TextButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(bottom = 72.dp)
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    onClick = {
                        navController.navigate("AddProduct")
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (isLoading.value) {
                            CircularProgressIndicator(color = Color.White)
                        } else {
                            Text(
                                text = "Adicionar Produto",
                                fontSize = 24.sp
                            )
                            Text(
                                text = "+",
                                color = Color.White,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            }
        }

        CreateBottomButtons(
            clickMenu = {},
            clickCarrinho = { navController.navigate("CarrinhosScreen") },
            clickProdutos = { navController.navigate("ProdutosScreen") },
            clickLogout = {
                try {
                    viewModelUtilizador.signOut()
                    Log.d("MenuUtilizadorScreen", "Logout efetuado com sucesso")
                    navController.navigate("Login")
                } catch (e: Exception) {
                    Log.d("MenuUtilizadorScreen", "Erro ao dar logout: $e")
                }
            }
        )
    }
}



@Composable
fun ProductItemBox(nome: String, preco: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clickable(onClick = onClick) // Add click functionality
    ) {
        Column {
            Text(
                text = nome,
                fontSize = 20.sp,
                color = Color.Black
            )
            Text(
                text = preco,
                fontSize = 18.sp,
                color = Color.Gray
            )
        }
    }
}
