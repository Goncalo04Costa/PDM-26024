package com.example.carrinhodecompras.view


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.carrinhodecompras.viewmodel.RegistoUtilizadorViewModel

@Composable
fun RegistoUtilizador(
    viewModel: RegistoUtilizadorViewModel,
    navController: NavController,
    username: String
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Função para criar o usuário
    fun createAccount() {
        if (password.text != confirmPassword.text) {
            errorMessage = "As senhas não coincidem!"
            return
        }

        isLoading = true
        viewModel.createAccount(email.text, password.text).let { success ->
            isLoading = false
            if (success) {
                navController.navigate("MenuUtilizador/${username}")
            } else {
                errorMessage = "Erro ao criar a conta. Tente novamente."
            }
        }
    }

    // Tela de Registro de Utilizador
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Registre-se", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        // Campo de e-mail
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Campo de senha
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Confirmar senha
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Mostrar mensagem de erro, se houver
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Botão de registro
        Button(
            onClick = { createAccount() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Criar Conta")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegistoUtilizador() {
    // Substitua com a ViewModel adequada para o preview
    RegistoUtilizador(viewModel = viewModel(), navController = rememberNavController(), username = "username")
}
