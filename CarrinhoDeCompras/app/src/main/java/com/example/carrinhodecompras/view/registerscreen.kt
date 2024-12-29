package com.example.carrinhodecompras.view

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.example.carrinhodecompras.Classes.Utilizador
import com.example.carrinhodecompras.UIElements.CreateText
import com.example.carrinhodecompras.UIElements.CreateTextField
import com.example.carrinhodecompras.viewmodel.MenuUtilizadorViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(viewModel: MenuUtilizadorViewModel,
                   navController: NavController) {
    var registerEmail by remember { mutableStateOf("") }
    var registerPassword by remember { mutableStateOf("") }
    var registerMessage by remember { mutableStateOf("") }
    var registerIsNotSuccessfull by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CreateTextField(registerEmail, Modifier
                .fillMaxWidth()
                .padding(16.dp),
                "Email:",
                valueChange = { registerEmail = it }, KeyboardType.Text, false)
            CreateTextField(registerPassword, Modifier
                .fillMaxWidth()
                .padding(16.dp),
                "Password",
                valueChange = { registerPassword = it }, KeyboardType.Password, true)

            Button(onClick = {
                if (registerEmail.isNullOrEmpty() || registerPassword.isNullOrEmpty()) {
                    registerIsNotSuccessfull = true
                    registerMessage = "Campos não preenchidos"
                } else {
                    try {
                        viewModel.viewModelScope.launch {
                            val resultRegister = viewModel.signIn(registerEmail, registerPassword)
                            if (resultRegister) {
                                val verUtlz = generateRegisterUserInfo(registerEmail, registerPassword)
                                val encodedUtlz = Uri.encode(verUtlz)
                                navController.navigate("MenuUtilizador/${encodedUtlz}")
                            } else {
                                registerIsNotSuccessfull = true
                                registerMessage = "Não foi possível registar o utilizador"
                            }
                        }
                    } catch (e: Exception) {
                        Log.d("RegisterScreen", "Exceção:${e}")
                    }
                }
            }) {
                Text("Registar")
            }

            TextButton(onClick = {
                val encodedUsername = Uri.encode(registerEmail)
                navController.navigate("LoginUtilizador/${encodedUsername}")
            }) {
                Text("Já tem conta? Inicie sessão")
            }

            if (registerIsNotSuccessfull) {
                CreateText(registerMessage, Modifier.fillMaxWidth().padding(16.dp),
                    Color.Black, TextAlign.Center, 32.sp)
            }
        }
    }
}

fun generateRegisterUserInfo(email: String, password: String): String {
    val verificarUtilizador = Utilizador(email, password)
    return Gson().toJson(verificarUtilizador)
}
