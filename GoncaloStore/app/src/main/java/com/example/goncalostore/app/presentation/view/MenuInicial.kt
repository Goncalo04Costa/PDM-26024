package com.example.goncalostore.app.presentation.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.goncalostore.app.domain.model.User
import com.example.goncalostore.app.presentation.viewmodel.LoginViewModel
import com.example.goncalostore.elements.CreateBottomButtons
import com.example.goncalostore.elements.CreateTextTitle
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson

@Composable
fun MenuUtilizador(viewModel: LoginViewModel,
                   navController: NavController,
                   utlzObject: String?) {
    val user = FirebaseAuth.getInstance().currentUser
    val userId = user?.uid
    val decodedUtlz = Gson().fromJson(utlzObject, User::class.java)
    decodedUtlz.uid = userId

    val nameUtlz = decodedUtlz.email.substringBefore("@gmail.com")

    // New color scheme
    val backgroundColor = Color(0xFFF1F8E9) // Light Green Background
    val textColor = Color(0xFF388E3C) // Darker Green for Text
    val titleColor = Color(0xFF1B5E20) // Deep Green for Title
    val buttonColor = Color(0xFF43A047) // Button Green

    Box(modifier = Modifier.fillMaxSize()) {
        CreateTextTitle(
            "Bem-vindo à GonçaloStore",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp, 0.dp, 0.dp),
            color = titleColor,
            size = 32.sp
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // You can add more content here if needed
        }

        CreateBottomButtons(
            clickMenu = {},
            clickCarrinho = { navController.navigate("CarrinhosScreen") },
            clickProdutos = {
                navController.navigate("ProdutosScreen")
                Log.d("Teste", "Clicked")
            },
            clickLogout = {
                try {
                    viewModel.signOut()
                    Log.d("MenuUtilizadorScreen", "Logout efetuado com sucesso")
                    navController.navigate("Login")
                } catch (e: Exception) {
                    Log.d("MenuUtilizadorScreen", "Erro ao dar logout: ${e}")
                }
            },
            buttonColor = buttonColor // Adjust button color here
        )
    }
}
