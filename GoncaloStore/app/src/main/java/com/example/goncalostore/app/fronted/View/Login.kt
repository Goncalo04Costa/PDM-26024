package com.example.goncalostore.app.fronted.View

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.goncalostore.app.fronted.ViewModel.LoginViewModel
import com.example.goncalostore.app.navigation.Routes
import com.example.goncalostore.app.navigation.Routes.FEED
import com.example.goncalostore.app.ui.buttons.CustomButton
import com.example.goncalostore.app.ui.buttons.CustomTextField
import com.example.goncalostore.app.ui.buttons.drawCircles
import com.example.goncalostore.app.ui.colors.LightBlue
import com.example.goncalostore.app.ui.colors.Orange
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    val loginViewModel: LoginViewModel = viewModel()


    val email by loginViewModel.email.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val errorMessage by loginViewModel.errorMessage.collectAsState()
    val isLoading by loginViewModel.isLoading.collectAsState()
    val isLoginSuccessful by loginViewModel.isLoginSuccessful.collectAsState()

    var showPassword by remember { mutableStateOf(false) }


    LaunchedEffect(isLoginSuccessful) {
        if (isLoginSuccessful) {
            navController.navigate(FEED) {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        drawCircles(LightBlue, "Login", 35.sp)
        drawCircles("bgg", 0.92f, 0.5f, 250f, 0f)

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Email",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                CustomTextField(
                    placeholder = "Escreva aqui...",
                    value = email,
                    onValueChange = { loginViewModel.onEmailChange(it) }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 32.dp)
            ) {
                Text(
                    text = "Palavra-Passe",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                CustomTextField(
                    placeholder = "Escreva aqui...",
                    value = password,
                    onValueChange = { loginViewModel.onPasswordChange(it) }
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { loginViewModel.onPasswordChange(it) },
                    placeholder = { Text("Escreva aqui...") },
                    visualTransformation = if (showPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        if (showPassword){
                            IconButton(onClick = { showPassword = false }) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "hide_password"
                                )
                            }
                        } else {
                            IconButton(
                                onClick = { showPassword = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "hide_password"
                                )
                            }
                        }
                    }
                )
            }

            // Mensagem de erro, se houver
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Botão de login
            CustomButton(
                label = if (isLoading) "..." else "LOGIN",
                color = LightBlue,
                onClick = {
                    if (!isLoading) {
                        // Lança a corrotina para chamar a função suspensa
                        loginViewModel.viewModelScope.launch {
                            loginViewModel.signIn(email, password)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(0.32f)
            )

            Text(
                text = "Criar Conta",
                color = Orange,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .padding(bottom = 65.dp)
                    .clickable { navController.navigate(Routes.REGIST) }

            )
        }
    }
}