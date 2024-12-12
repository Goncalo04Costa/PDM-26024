package com.example.loja.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.loja.viewmodel.RegistoUtilizadorViewModel
import kotlinx.coroutines.launch

@Composable
fun RegistoUtilizador(viewModel: RegistoUtilizadorViewModel,
                      navController: NavController,
                      email: String? = null) {
    var userEmail by remember { mutableStateOf(email ?: "") }
    var userPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

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
            Text(
                text = "Registo",
                color = Color.Black,
                fontSize = 32.sp,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                textAlign = TextAlign.Center
            )

            // Email Field
            TextField(
                value = userEmail,
                onValueChange = { userEmail = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                singleLine = true
            )

            // Password Field
            TextField(
                value = userPassword,
                onValueChange = { userPassword = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                singleLine = true
            )

            // Confirm Password Field
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                singleLine = true
            )

            // Register Button
            Button(
                onClick = {
                    if (verifyPasswords(userPassword, confirmPassword)) {
                        isLoading = true
                        successMessage = null
                        // Launching a coroutine to call the suspend function
                        viewModel.viewModelScope.launch {
                            try {
                                val isSuccessful = viewModel.createAccount(userEmail, userPassword)
                                isLoading = false
                                if (isSuccessful) {
                                    successMessage = "Registo efetuado com sucesso para a conta: $userEmail"
                                } else {
                                    successMessage = "Registo não efetuado"
                                }
                            } catch (e: Exception) {
                                successMessage = "Ocorreu um erro: ${e.message}"
                                isLoading = false
                            }
                        }
                    } else {
                        successMessage = "As passwords não coincidem"
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                enabled = userEmail.isNotEmpty() && userPassword.isNotEmpty() && confirmPassword.isNotEmpty() && !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
                } else {
                    Text("Registar")
                }
            }

            // Success/Error Message
            successMessage?.let {
                Text(
                    text = it,
                    color = if (it.contains("sucesso", ignoreCase = true)) Color.Green else Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )
            }
        }
    }
}

fun verifyPasswords(firstPw: String, secondPw: String): Boolean {
    return firstPw == secondPw
}
