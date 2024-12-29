package com.example.carrinhodecompras.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.carrinhodecompras.presentation.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val state = authViewModel.authState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        // Campo para o email
        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            isPassword = false,
            errorMessage = state.error
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo para a senha
        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Senha",
            isPassword = true,
            errorMessage = state.error
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botão de login
        Button(onClick = { authViewModel.login(email, password) }) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botão para navegar para a tela de registro
        Button(onClick = onNavigateToRegister) {
            Text("Registrar-se")
        }

        // Indicador de progresso enquanto carrega
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        // Exibe o erro, caso exista
        state.error?.let { error ->
            Text(error, color = Color.Red)
        }

        // Quando o login for bem-sucedido, navega para a próxima tela
        state.user?.let {
            LaunchedEffect(Unit) {
                onLoginSuccess()
            }
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    errorMessage: String? = null
) {

    val errorColor = if (errorMessage != null) Color.Red else Color.Transparent

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier.fillMaxWidth(),
        isError = errorMessage != null,
        singleLine = true,
        supportingText = {
            if (errorMessage != null) {
                Text(errorMessage, color = Color.Red)
            }
        }
    )
}
