package com.example.goncalostore.app.fronted.View

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalContext
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
import com.example.goncalostore.app.backend.models.User
import com.example.goncalostore.app.fronted.ViewModel.LoginViewModel
import com.example.goncalostore.app.fronted.ViewModel.UserViewModel
import com.example.goncalostore.app.navigation.Routes.FEED
import com.example.goncalostore.app.ui.buttons.CustomButton
import com.example.goncalostore.app.ui.buttons.CustomTextField
import com.example.goncalostore.app.ui.buttons.drawCircles
import com.example.goncalostore.app.ui.colors.LightBlue
import com.example.goncalostore.app.ui.colors.Orange
import kotlinx.coroutines.launch

@Composable
fun RegisterUserScreen(navController: NavController) {
    val userViewModel: UserViewModel = viewModel()
    val context = LocalContext.current

    // Campos de entrada de dados
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Desenho das formas de fundo (opcional)
        drawCircles(LightBlue, "Registar \nUsuário", 25.sp)

        // Conteúdo principal
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
                // Nome
                Text(
                    text = "Nome *",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                CustomTextField(
                    placeholder = "Qual o seu nome",
                    value = nome,
                    onValueChange = { nome = it }
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Email
                Text(
                    text = "Email *",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                CustomTextField(
                    placeholder = "Indique o seu email",
                    value = email,
                    onValueChange = { email = it }
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Senha
                Text(
                    text = "Senha *",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                CustomTextField(
                    placeholder = "Indique a sua senha",
                    value = password,
                    onValueChange = { password = it }
                )
                Spacer(modifier = Modifier.height(12.dp))

            }


            CustomButton(
                label = "Registar Utilizador",
                color = LightBlue,
                modifier = Modifier.fillMaxWidth(0.50f),
                onClick = {
                    if (nome.isEmpty() || email.isEmpty() || password.isEmpty() ) {
                        Toast.makeText(
                            context,
                            "Por favor, preencha todos os campos obrigatórios.",
                            Toast.LENGTH_LONG
                        ).show()
                        return@CustomButton
                    }


                    val user = User(
                        nome = nome,
                        email = email,
                        password = password,
                        carrinhos = listOf()
                    )


                    userViewModel.viewModelScope.launch {
                        userViewModel.addUser(
                            user,
                            onFailure = { msg ->
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            },
                            onSuccess = { msg ->
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                navController.navigate("feed") {
                                    popUpTo("regist") {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }
                }
            )
        }
    }
}
