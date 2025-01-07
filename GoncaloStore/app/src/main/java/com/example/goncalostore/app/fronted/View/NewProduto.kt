package com.example.goncalostore.app.fronted.View

import com.example.goncalostore.app.fronted.ViewModel.ProdutoViewModel
import com.example.goncalostore.app.ui.buttons.CustomButton
import com.example.goncalostore.app.ui.buttons.CustomTextField
import com.example.goncalostore.app.ui.buttons.drawCircles
import com.example.goncalostore.app.ui.colors.LightBlue
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.goncalostore.app.backend.models.Produto
import kotlinx.coroutines.launch

@Composable
fun RegisterProductScreen(navController: NavController) {

    val produtoViewModel: ProdutoViewModel = viewModel()
    val context = LocalContext.current

    // States for input fields
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background decoration
        drawCircles(LightBlue, "Registrar \nProduto", 25.sp)
        drawCircles("bgg", 0.61f, 0.5f, 0f, 0f)

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
                    placeholder = "Introduza o nome do produto",
                    value = nome,
                    onValueChange = { nome = it }
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Descrição
                Text(
                    text = "Descrição *",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                CustomTextField(
                    placeholder = "Introduza a descrição do produto",
                    value = descricao,
                    onValueChange = { descricao = it }
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Preço
                Text(
                    text = "Preço *",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                CustomTextField(
                    placeholder = "Introduza o preço",
                    value = preco,
                    onValueChange = { preco = it }
                )
            }

            CustomButton(
                label = "Registrar Produto",
                color = LightBlue,
                modifier = Modifier.fillMaxWidth(0.56f),
                onClick = {
                    if (nome.isEmpty() || descricao.isEmpty() || preco.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Por favor, preencha todos os campos obrigatórios.",
                            Toast.LENGTH_LONG
                        ).show()
                        return@CustomButton
                    }

                    val precoInt = preco.toIntOrNull()
                    if (precoInt == null || precoInt < 0) {
                        Toast.makeText(
                            context,
                            "Valor Inválido.",
                            Toast.LENGTH_LONG
                        ).show()
                        return@CustomButton
                    }

                    // Asynchronous call to save product
                    produtoViewModel.viewModelScope.launch {
                        try {
                            val produto = Produto(
                                nome = nome,
                                descricao = descricao,
                                preco = precoInt
                            )

                            produtoViewModel.addProduto(
                                produto,
                                onFailure = { msg ->
                                    Log.d("RegisterProduct", "Erro recebido: $msg")
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                },
                                onSuccess = { msg ->
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    navController.navigate("listproducts") {
                                        popUpTo("register_product_screen") { inclusive = true }
                                    }
                                }
                            )
                        } catch (e: Exception) {
                            Log.e("RegisterProductScreen", "Erro ao registrar o produto", e)
                        }
                    }
                }
            )
        }
    }
}
