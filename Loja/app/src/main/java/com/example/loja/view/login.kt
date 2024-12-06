package com.example.loja.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.loja.viewmodel.LoginViewModel

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }
}

@Composable
fun LoginScreen(viewModel: LoginViewModel = LoginViewModel()) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    val erroMensagem by viewModel.erroMensagem

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.loginUsuario(email, senha) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }
        if (erroMensagem.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = erroMensagem,
                color = if (erroMensagem == "Login bem-sucedido!") Color.Green else Color.Red,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
