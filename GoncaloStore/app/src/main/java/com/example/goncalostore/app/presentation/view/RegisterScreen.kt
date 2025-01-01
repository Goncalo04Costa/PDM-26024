package com.example.goncalostore.app.presentation.view

import com.example.goncalostore.app.presentation.viewmodel.NewUserViewModel
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
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
import com.example.goncalostore.elements.CreateText
import com.example.goncalostore.elements.CreateTextField
import com.example.goncalostore.elements.CreateTextTitle
import kotlinx.coroutines.launch


@Composable
fun NewUser(viewModel: NewUserViewModel,
            navController: NavController,
            email: String? = null) {
    var userEmail by remember { mutableStateOf(email ?: "") }
    var userPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CreateTextTitle(
                "Adira já!", Modifier.fillMaxWidth()
                    .padding(16.dp), Color.Black, 32.sp
            )
            CreateTextField(
                userEmail, Modifier.fillMaxWidth().padding(16.dp), "Email:",
                valueChange = { userEmail = it }, KeyboardType.Text, false
            )

            CreateTextField(
                userPassword, Modifier.fillMaxWidth().padding(16.dp), "Password:",
                valueChange = { userPassword = it }, KeyboardType.Password, true
            )

            CreateTextField(
                confirmPassword, Modifier.fillMaxWidth().padding(16.dp), "Confirm Password:",
                valueChange = { confirmPassword = it }, KeyboardType.Password, true
            )

            Button(
                onClick = {
                    if (verifyPasswords(userPassword, confirmPassword)) {
                        Log.d("RegisterScreen", "Passwords são iguais")
                        try {
                            viewModel.viewModelScope.launch {
                                val isSuccessful = viewModel.createAccount(userEmail, userPassword)
                                if (isSuccessful) {
                                    successMessage = "Registo efetuado com sucesso para a conta: ${userEmail}"
                                } else {
                                    successMessage = "Registo não efetuado"
                                }
                            }
                        } catch (e: Exception) {
                            successMessage = "Ocorreu um erro: ${e}"
                        }
                    } else {
                        Log.d("RegisterScreen", "Passwords não são iguais")
                        successMessage = "Passwords não coincidem."
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = mediumGreen)
            ) {
                Text("Registar", color = Color.White)
            }


            successMessage?.let {
                CreateText(
                    successMessage!!, Modifier.fillMaxWidth(),
                    if (successMessage!!.contains("sucesso")) lightGreen else errorGreen,
                    TextAlign.Center, 16.sp
                )
            }
        }
    }
}

fun verifyPasswords(firstPw: String, secondPw: String): Boolean {
    return (firstPw == secondPw)
}
