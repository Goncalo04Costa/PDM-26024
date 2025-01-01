package com.example.goncalostore.app.presentation.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.goncalostore.app.domain.model.Cart
import com.example.goncalostore.app.presentation.viewmodel.CartsViewModel
import com.example.goncalostore.app.presentation.viewmodel.LoginViewModel
import com.example.goncalostore.elements.CreateBottomButtons
import com.example.goncalostore.elements.CreateText
import com.example.goncalostore.elements.CreateTextTitle
import kotlinx.coroutines.launch

@Composable
fun CarrinhosScreen(
    viewModelCarrinho: CartsViewModel,
    viewModelUtilizador: LoginViewModel,
    navController: NavController
) {
    val listCarrinhos = remember { mutableStateOf(emptyList<Cart>()) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val carrinhos = viewModelCarrinho.FetchCarrinhos()
            listCarrinhos.value = carrinhos
            isLoading.value = false
        } catch (e: Exception) {
            Log.d("CarrinhosScreen", "Erro:${e}")
        }
    }

    val backgroundColor = Color(0xFFE8F5E9) // Light green background
    val cardBorderColor = Color(0xFF388E3C) // Darker green for card border
    val buttonColor = Color(0xFF2C6E2F) // Darker green for button
    val textColor = Color(0xFF1B5E20) // Dark green for text
    val floatingButtonColor = Color(0xFF43A047) // Green for floating action button

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading.value) {
                item {
                    CircularProgressIndicator()
                }
            } else if (listCarrinhos.value.isEmpty()) {
                item {
                    CreateTextTitle(
                        "SemCarrinhos", Modifier.fillMaxWidth()
                            .padding(16.dp), Color.Black, 32.sp
                    )

                }
            } else {
                items(listCarrinhos.value) { item ->
                    CarrinhoItemBox(
                        item.donoCarrinho,
                        item,
                        cardBorderColor
                    )
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            onClick = {
                navController.navigate("AddCarrinho")
            },
            containerColor = floatingButtonColor
        ) {
            Text("+", color = Color.White)
        }

        CreateBottomButtons(
            clickMenu = {},
            clickCarrinho = {navController.navigate("CarrinhosScreen")},
            clickProdutos = {  navController.navigate("ProdutosScreen") },
            clickLogout = {
                try {
                    viewModelUtilizador.signOut()
                    Log.d("MenuUtilizadorScreen", "Logout efetuado com sucesso")
                    navController.navigate("Login")
                } catch (e: Exception) {
                    Log.d("MenuUtilizadorScreen", "Erro ao dar logout:${e}")
                }
            },
            buttonColor = buttonColor
        )
    }
}

@Composable
fun CarrinhoItemBox(dono: String?, carrinho: Cart, borderColor: Color) {
    var isSheetOpen by remember { mutableStateOf(false) }
    if (!dono.isNullOrEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(1.dp, borderColor, shape = RoundedCornerShape(12.dp)),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CreateText(dono, Modifier.align(Alignment.CenterVertically), Color.Black, null, 18.sp)
                    Button(onClick = { isSheetOpen = true }, colors = ButtonDefaults.buttonColors(containerColor = borderColor)) {
                        Text("Ver produtos", color = Color.White)
                    }
                }
            }
        }

        if (isSheetOpen) {
            Dialog(onDismissRequest = { isSheetOpen = false }) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (carrinho.listaProdutos.isEmpty()) {
                            item {
                                CreateText(
                                    "Não existem produtos",
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Black,
                                    textAlign = TextAlign.Center,
                                    24.sp
                                )
                            }
                        } else {
                            items(carrinho.listaProdutos) { item ->
                                ProductItemBox(
                                    nome = item.produto?.nome,
                                    descricao = item.produto?.descricao,
                                    preco = item.produto?.preco.toString()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
