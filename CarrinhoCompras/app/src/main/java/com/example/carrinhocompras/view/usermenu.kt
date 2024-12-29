package com.example.carrinhodecompras.Presentation.Screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.carrinhocompras.UIelements.CreateBottomButtons
import com.example.carrinhocompras.UIelements.CreateTextTitle
import com.example.carrinhocompras.models.user
import com.example.carrinhocompras.viewmodel.userviewmodel
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson

@Composable
fun MenuUtilizador(
    viewModel: userviewmodel,
    navController: NavController,
    utlzObject: String
) {
    val user = FirebaseAuth.getInstance().currentUser
    val userId = user?.uid

    // Parse manual do JSON
    val jsonObject = Gson().fromJson(utlzObject, Map::class.java)
    val userEmail = jsonObject["email"]?.toString()
    val nameUtlz = userEmail?.substringBefore("@gmail.com") ?: "Usuário"

    val currentUser = user(
        email = userEmail.orEmpty(),
        password = "defaultPassword",
        carrinhoCompras = emptyList(),
        uid = userId.orEmpty(),
        produtos = emptyList()
    )


    Log.d("MenuUtilizadorScreen", "Objeto: $currentUser")

    Box(modifier = Modifier.fillMaxSize()) {
        CreateTextTitle(
            text = "Bem-vindo $nameUtlz", // Usando a função já definida
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            color = Color.Black,
            fontSize = 32.sp
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Elementos adicionais, se necessário
        }

        CreateBottomButtons(
            clickMenu = {},
            clickCarrinho = { navController.navigate("CarrinhosScreen") },
            clickProdutos = {
                navController.navigate("ProdutosScreen")
                Log.d("MenuUtilizadorScreen", "Clicked Produtos")
            },
            clickLogout = {
                try {
                    viewModel.signOut()
                    Log.d("MenuUtilizadorScreen", "Logout efetuado com sucesso")
                    navController.navigate("Login")
                } catch (e: Exception) {
                    Log.d("MenuUtilizadorScreen", "Erro ao dar logout: ${e.message}")
                }
            }
        )
    }
}

@Composable
fun CreateTextTitle(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    fontSize: TextUnit = 24.sp
) {
    BasicText(
        text = text,
        modifier = modifier.fillMaxWidth(),
        style = TextStyle(
            color = color,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    )
}
