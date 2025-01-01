package com.example.goncalostore.app.presentation.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.goncalostore.app.domain.model.Product
import com.example.goncalostore.app.presentation.viewmodel.LoginViewModel
import com.example.goncalostore.app.presentation.viewmodel.ProdutosViewModel
import com.example.goncalostore.elements.CreateBottomButtons
import com.example.goncalostore.elements.CreateText

@Composable
fun MenuProdutos(
    viewModelProdutos: ProdutosViewModel,
    viewModelUtilizador: LoginViewModel,
    navController: NavController,
) {
    val listProducts = remember { mutableStateOf(emptyList<Product>()) }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val products = viewModelProdutos.FetchProducts()
            listProducts.value = products
            isLoading.value = false
        } catch (e: Exception) {
            Log.d("ProdutosScreen", "Erro:${e}")
            errorMessage.value = "Erro ao carregar produtos. Tente novamente."
            isLoading.value = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Show loading indicator while data is being fetched
        if (isLoading.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        // Show error message if there was an issue fetching data
        errorMessage.value?.let {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = it,
                    color = Color.Red,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Display product list
        if (errorMessage.value == null && !isLoading.value) {
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
                            fontSize = 32.sp
                        )
                    }
                } else {
                    items(listProducts.value, key = { it.nome!! }) { item ->
                        ProductItemBox(
                            nome = item.nome,
                            descricao = item.descricao,
                            preco = item.preco.toString()
                        )
                    }
                }
            }
        }

        // Bottom "Add Product" button
        TextButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 72.dp)
                .background(color = Color(0xFF4CAF50), shape = RoundedCornerShape(12.dp)),
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
                Text(text = "Adicionar Produto", fontSize = 24.sp, color = Color.White)
                Text(text = "+", color = Color.White, fontSize = 24.sp)
            }
        }

        // Bottom navigation buttons
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
                    Log.d("MenuUtilizadorScreen", "Erro ao dar logout:${e}")
                }
            }
        )
    }
}

@Composable
fun ProductItemBox(nome: String?, descricao: String?, preco: String?) {
    if (!nome.isNullOrEmpty() || !descricao.isNullOrEmpty() || !preco.isNullOrEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(1.dp, Color(0xFF388E3C), shape = RoundedCornerShape(8.dp)) // Bordas verde escuro
                .background(Color(0xFF81C784), shape = RoundedCornerShape(8.dp)) // Verde claro
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (nome != null) {
                        CreateText(nome, Modifier.align(Alignment.Top), Color.Black, null, 16.sp)
                    }
                    if (preco != null) {
                        CreateText(preco + "€", Modifier.align(Alignment.CenterVertically), Color.Black,
                            TextAlign.End, 32.sp)
                    }
                }
                if (descricao != null) {
                    CreateText(descricao, Modifier.padding(top = 8.dp), Color.Black, null, 16.sp) // Verde escuro para a descrição
                }
            }
        }
    }
}
