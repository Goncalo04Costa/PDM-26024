package com.example.goncalostore.app.presentation.view

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.goncalostore.app.domain.model.User
import com.example.goncalostore.app.presentation.viewmodel.LoginViewModel
import com.example.goncalostore.elements.CreateText
import com.example.goncalostore.elements.CreateTextField
import com.example.goncalostore.elements.CreateTextTitle
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel,
                navController: NavController) {
    var loginUtilizador by remember { mutableStateOf("") }
    var passwordUtilizador by remember { mutableStateOf("") }
    var loginMessage by remember { mutableStateOf("") }
    var loginIsNotSuccessfull by remember { mutableStateOf(false) }

    val backgroundColor = Color(0xFFA5D6A7) // Light green background
    val buttonColor = Color(0xFF388E3C) // Darker green for button
    val textColor = Color(0xFF1B5E20) // Dark green for text

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            CreateTextTitle(
                "GoncaloStore", Modifier.fillMaxWidth()
                    .padding(16.dp), Color.Black, 32.sp
            )


            CreateTextField(
                loginUtilizador, Modifier
                    .fillMaxWidth()
                    .padding(16.dp), "Email:",
                valueChange = { loginUtilizador = it }, KeyboardType.Text, false
            )
            CreateTextField(
                passwordUtilizador, Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                "Password",
                valueChange = { passwordUtilizador = it }, KeyboardType.Password, true
            )

            // Botão de login
            Button(
                onClick = {
                    if (loginUtilizador.isNullOrEmpty() || passwordUtilizador.isNullOrEmpty()) {
                        loginIsNotSuccessfull = true
                        loginMessage = "Preencha os dados completos"
                    } else {
                        try {
                            viewModel.viewModelScope.launch {
                                val resultLogin = viewModel.signIn(loginUtilizador, passwordUtilizador)
                                if (resultLogin) {
                                    val verUtlz = generateUserInfo(loginUtilizador, passwordUtilizador)
                                    val encodedUtlz = Uri.encode(verUtlz)
                                    navController.navigate("MenuUtilizador/${encodedUtlz}")
                                } else {
                                    loginIsNotSuccessfull = true
                                    loginMessage = "Não foi possível iniciar a sessão"
                                }
                            }
                        } catch (e: Exception) {
                            Log.d("LoginScreen", "Exceção:${e}")
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text("Entrar", color = Color.White)
            }

            // Botão para navegação para registro
            TextButton(onClick = {
                val encodedUsername = Uri.encode(loginUtilizador)
                navController.navigate("RegistoUtilizador/${encodedUsername}")
            }) {
                Text("Ainda não tem conta? Registe-se", color = buttonColor)
            }

            // Mensagem de erro de login
            if (loginIsNotSuccessfull) {
                CreateText(
                    loginMessage, Modifier.fillMaxWidth().padding(16.dp),
                    Color.Red, TextAlign.Center, 32.sp
                )
            }
        }
    }
}

fun generateUserInfo(email: String, password: String): String {
    val verificarUtilizador = User(email, password)
    return Gson().toJson(verificarUtilizador)
}
