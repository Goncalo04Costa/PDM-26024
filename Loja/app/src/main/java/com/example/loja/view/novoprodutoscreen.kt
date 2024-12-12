package com.example.loja.view


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.example.loja.classes.Produto
import com.example.loja.viewmodel.ProdutosViewModel
import com.example.loja.UIElements.CreateTextField
import com.example.loja.UIElements.CreateTextTitle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


@Composable
fun AddProduct(viewModel: ProdutosViewModel, navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser
    val userId = user?.uid
    var nameProduct by remember { mutableStateOf("") }
    var priceProduct by remember { mutableStateOf("") }
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
            CreateTextTitle("Adicionar Produto", Modifier.fillMaxWidth().padding(16.dp), Color.Black, 32.sp)

            CreateTextField(
                nameProduct, Modifier.fillMaxWidth().padding(16.dp),
                "Nome Produto", valueChange = { nameProduct = it },
                KeyboardType.Text, false
            )

            CreateTextField(
                priceProduct, Modifier.fillMaxWidth().padding(16.dp),
                "Preço Produto", valueChange = { priceProduct = it },
                KeyboardType.Number, false
            )

            Button(onClick = {
                val newProduct = generateObject(nameProduct, priceProduct, userId)
                if (!newProduct.isDefault()) {
                    Log.d("AddProductScreen", "Dados produto: ${newProduct}")
                    viewModel.viewModelScope.launch {
                        try {
                            val result = viewModel.addProduct(newProduct)
                            Log.d("AddProductScreen", "Produto adicionado com sucesso: ${result}")
                            isAddedText = "Produto adicionado com sucesso"
                            isAdded = true
                        } catch (e: Exception) {
                            isAddedText = "Produto não adicionado."
                            Log.d("AddProductScreen", "Ocorreu um erro: ${e}")
                        }
                    }
                } else {
                    Log.d("AddProductScreen", "Erro ao inserir o produto.")
                    isAddedText = "Produto não adicionado."
                }
            }) {
                Text("Adicionar produto")
            }

            // Display success or error message
            Text(isAddedText)

            Button(onClick = { navController.navigate("ProdutosScreen") }) {
                Text("Voltar")
            }
        }
    }
}



fun generateObject(name: String, price: String, uid: String? = ""): Produto {
    return if (name.isNotEmpty() && price.isNotEmpty()) {
        try {
            val priceInt = price.toIntOrNull() ?: 0
            val id = 0
            val quantidade = 0
            Produto(id, name, priceInt, quantidade)  // Using the constructor with all four parameters
        } catch (e: Exception) {
            Log.d("AddProductScreen", "Ocorreu um erro: ${e}")
            Produto(0, "", 0, 0)  // Default values in case of error
        }
    } else {
        Log.d("AddProductScreen", "Erro: Nome ou Preço inválidos.")
        Produto(0, "", 0, 0)  // Return default empty object if validation fails
    }
}

