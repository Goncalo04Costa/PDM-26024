package com.example.goncalostore.app.presentation.view

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.goncalostore.app.presentation.viewmodel.ProdutosViewModel
import com.example.goncalostore.app.domain.model.Product
import com.example.goncalostore.elements.CreateTextField
import com.example.goncalostore.elements.CreateTextTitle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

// Definir tons de verde
val lightGreen = Color(0xFF81C784) // Verde claro
val mediumGreen = Color(0xFF388E3C) // Verde médio
val darkGreen = Color(0xFF1B5E20) // Verde escuro
val errorGreen = Color(0xFFD32F2F) // Vermelho para erro

@Composable
fun AddProduct(viewModel: ProdutosViewModel, navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser
    val userId = user?.uid
    var nameProduct by remember { mutableStateOf("") }
    var priceProduct by remember { mutableStateOf("") }
    var descriptionProduct by remember { mutableStateOf("") }
    var isAddedText by remember { mutableStateOf("") }
    var isAdded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CreateTextTitle(
                "Adicionar Produto",
                Modifier.fillMaxWidth().padding(16.dp),
                Color.Black, 32.sp
            )
            CreateTextField(
                nameProduct,
                Modifier.fillMaxWidth().padding(16.dp),
                "Nome Produto",
                valueChange = { nameProduct = it },
                KeyboardType.Text,
                false
            )
            CreateTextField(
                priceProduct,
                Modifier.fillMaxWidth().padding(16.dp),
                "Preço Produto",
                valueChange = { priceProduct = it },
                KeyboardType.Text,
                false
            )
            CreateTextField(
                descriptionProduct,
                Modifier.fillMaxWidth().padding(16.dp),
                "Descrição Produto",
                valueChange = { descriptionProduct = it },
                KeyboardType.Text,
                false
            )

            Button(
                onClick = {
                    val newProduct = generateObject(
                        nameProduct, priceProduct, descriptionProduct, userId
                    )
                    if (!newProduct.isDefault()) {
                        Log.d("AddProductScreen", "Dados produto:${newProduct}")
                        viewModel.viewModelScope.launch {
                            try {
                                val result = viewModel.AddProduct(newProduct)
                                Log.d("AddProductScreen", "Produto adicionado com sucesso:${result}")
                                isAddedText = "Produto adicionado com sucesso"
                                isAdded = true
                            } catch (e: Exception) {
                                isAddedText = "Produto não adicionado."
                                Log.d("AddProductScreen", "Ocorreu um erro:${e}")
                            }
                        }
                    } else {
                        Log.d("AddProductScreen", "Erro ao inserir o produto.")
                        isAddedText = "Produto não adicionado."
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = mediumGreen)
            ) {
                Text("Adicionar produto", color = Color.White)
            }

            // Exibir a mensagem de sucesso ou erro
            if (isAdded) {
                Text(isAddedText, color = lightGreen, fontSize = 18.sp)
            } else {
                Text(isAddedText, color = errorGreen, fontSize = 18.sp)
            }

            Button(
                onClick = {
                    navController.navigate("ProdutosScreen")
                },
                colors = ButtonDefaults.buttonColors(containerColor = darkGreen)
            ) {
                Text("Voltar", color = Color.White)
            }
        }
    }
}

fun generateObject(name: String, price: String, description: String, uid: String? = ""): Product {
    return if (name.isNotEmpty() || price.isNotEmpty() || description.isNotEmpty() || uid != null) {
        try {
            val priceInt = price.toInt()
            val newProduct = Product(name, priceInt, description, uid)
            newProduct
        } catch (e: Exception) {
            Log.d("AddProductScreen", "Ocorreu um erro :${e}")
            Product("", 0, "", "")
        }
    } else {
        Log.d("AddProductScreen", "Ocorreu um erro")
        Product("", 0, "", "")
    }
}
