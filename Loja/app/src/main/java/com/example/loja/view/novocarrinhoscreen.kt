package com.example.loja.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.loja.classes.Produto
import com.example.loja.classes.Carrinho
import com.example.loja.classes.ProdutoCarrinho
import com.example.loja.viewmodel.CarrinhosViewModel
import com.example.loja.UIElements.CreateText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import com.example.loja.viewmodel.ProdutosViewModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AddCarrinho(
    viewModelCarrinho: CarrinhosViewModel,
    viewModelProduto: ProdutosViewModel,
    navController: NavController
) {
    val listProducts = remember { mutableStateOf(emptyList<Produto>()) }
    val listProductsCarrinho = remember { mutableStateOf(mutableListOf<ProdutoCarrinho>()) }
    val isLoading = remember { mutableStateOf(false) }

    // Fetch produtos uma vez ao montar o composable
    LaunchedEffect(Unit) {
        try {
            val fetchedProducts = viewModelProduto.fetchProducts()
            listProducts.value = fetchedProducts
            Log.d("AddCarrinhoScreen", "Produtos fetched.")
        } catch (e: Exception) {
            Log.d("AddCarrinhoScreen", "Erro ao buscar produtos: ${e}")
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(listProducts.value) { item ->
                ProductItemBoxCarrinho(
                    nome = item.nome,
                    preco = item.preco.toString(),
                    adicionarProduto = { quantity ->
                        val produtoCarrinho = ProdutoCarrinho(item, quantity)
                        listProductsCarrinho.value.add(produtoCarrinho)
                    }
                )
            }
            item {
                TextButton(onClick = {
                    val newCarrinho = GenerateCarrinho(listProductsCarrinho.value)
                    viewModelCarrinho.viewModelScope.launch {
                        try {
                            isLoading.value = true
                            // Chama a função para salvar o carrinho no Firestore
                            val resultCarrinho = viewModelCarrinho.salvarCarrinho(newCarrinho)
                            Log.d("AddCarrinhoScreen", "Carrinho adicionado: $resultCarrinho")
                            isLoading.value = false
                        } catch (e: Exception) {
                            Log.d("AddCarrinhoScreen", "Erro ao adicionar o carrinho: $e")
                            isLoading.value = false
                        }
                    }
                }) {
                    if (isLoading.value) {
                        Text("Carregando...")
                    } else {
                        Text("Adicionar ao carrinho")
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItemBoxCarrinho(
    nome: String?,
    preco: String?,
    adicionarProduto: (Int) -> Unit
) {
    var quantity by remember { mutableStateOf(0) } // Quantidade sendo lembrada

    // Exibição do produto se os dados não forem nulos
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            // Linha para mostrar o nome do produto e preço
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                nome?.let {
                    CreateText(it, Modifier.align(Alignment.Top), Color.Black, null, 16.sp)
                }
                preco?.let {
                    CreateText("$it €", Modifier.align(Alignment.CenterVertically), Color.Black, TextAlign.End, 16.sp)
                }
            }

            // Linha com botões para adicionar ou diminuir a quantidade
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { if (quantity > 0) quantity -= 1 }) {
                    Text("-")
                }

                CreateText(
                    "$quantity",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 16.dp),
                    color = Color.Black,
                    null,
                    16.sp
                )

                TextButton(onClick = { quantity += 1 }) {
                    Text("+")
                }

                // Botão para adicionar o produto ao carrinho
                TextButton(onClick = { adicionarProduto(quantity) }) {
                    Text("Adicionar ao carrinho")
                }
            }
        }
    }
}

fun GenerateCarrinho(listProducts: List<ProdutoCarrinho>): Carrinho {
    val userInstance = FirebaseAuth.getInstance().currentUser
    val nameUser = userInstance?.email?.substringBefore("@gmail.com")
    val newCarrinho = Carrinho(idCarrinho = generateUniqueCarrinhoId(), donoCarrinho = nameUser)

    listProducts.forEach { product ->
        newCarrinho.listaProdutos.add(product)
        Log.d("AddCarrinhoScreen", "Produto adicionado: ${product.produto} Quantidade: ${product.quantidade}")
    }

    return newCarrinho
}

// Geração de ID único para o carrinho
fun generateUniqueCarrinhoId(): String {
    return FirebaseFirestore.getInstance().collection("carrinhos").document().id // ID único baseado no documento do Firestore
}
