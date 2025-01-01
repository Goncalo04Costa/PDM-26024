package com.example.goncalostore.app.presentation.view

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
import com.example.goncalostore.app.domain.model.Cart
import com.example.goncalostore.app.domain.model.CartItem
import com.example.goncalostore.app.domain.model.Product
import com.example.goncalostore.app.presentation.viewmodel.CartsViewModel
import com.example.goncalostore.app.presentation.viewmodel.ProdutosViewModel
import com.example.goncalostore.elements.CreateText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun AddCarrinho(viewModelCarrinho: CartsViewModel,
                viewModelProduto: ProdutosViewModel,
                navController: NavController){
    val listProducts = remember { mutableStateOf(emptyList<Product>()) }
    var listProductsCarrinho = mutableListOf<CartItem>()
    LaunchedEffect(Unit) {
        try{
            viewModelProduto.viewModelScope.launch {
                listProducts.value = viewModelProduto.FetchProducts()
                Log.d("AddCarrinhoScreen","Produtos fetched.")
            }
        }catch(e:Exception){
            Log.d("AddCarrinhoScreen","Ocorreu um erro ao dar fetch dos produtos:${e}")
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()){
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            items(listProducts.value){item->
                ProductItemBoxCarrinho(
                    item.nome,
                    item.descricao,
                    item.preco.toString(),
                    adicionarProduto = {quantity ->
                        listProductsCarrinho.add(CartItem(item,quantity))
                    }
                )
            }
            item{
                TextButton(onClick = {
                    val newCarrinho = GenerateCarrinho(listProductsCarrinho)
                    viewModelCarrinho.viewModelScope.launch{
                        try{
                            val resultCarrinho = viewModelCarrinho.NewCart(newCarrinho)
                            Log.d("AddCarrinhoScreen","Carrinho adicionado:${resultCarrinho}")
                        }catch(e:Exception){
                            Log.d("AddCarrinhoScreen","Erro ao adicionar o carrinho:${e}")
                        }
                    }
                }) {
                    Text("Adicionar carrinho", color = Color.White)
                }
            }
        }
    }
}

fun GenerateCarrinho(listProducts: List<CartItem>): Cart {
    val userInstance = FirebaseAuth.getInstance().currentUser
    val nameUser = userInstance?.email?.substringBefore("@gmail.com")
    val newCarrinho = Cart(idCarrinho = "2", donoCarrinho = nameUser)
    if (listProducts.isEmpty())
        return newCarrinho

    for (product in listProducts) {
        newCarrinho.listaProdutos.add(product)
        Log.d("AddCarrinhoScreen", "Product added:${product.produto}")
        Log.d("AddCarrinhoScreen", "Product added:${product.quantidade}")
        Log.d("AddCarrinhoScreen", "List:${newCarrinho.listaProdutos}")
    }
    return newCarrinho
}

@Composable
fun ProductItemBoxCarrinho(nome: String?, descricao: String?, preco: String?,
                           adicionarProduto: (Int) -> Unit = {}) {
    var quantity by remember { mutableStateOf(0) }
    if (!nome.isNullOrEmpty() || !descricao.isNullOrEmpty() || !preco.isNullOrEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(1.dp, Color(0xFF388E3C), shape = RoundedCornerShape(8.dp)) // Bordas verde escuro
                .background(Color(0xFF81C784), shape = RoundedCornerShape(8.dp)) // Verde claro
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (nome != null) {
                        CreateText(nome, Modifier.align(Alignment.Top), Color.Black, null, 18.sp) // Preto para o nome
                    }
                    if (preco != null) {
                        CreateText(preco + "€", Modifier.align(Alignment.CenterVertically), Color.Black,
                            TextAlign.End, 16.sp)
                    }
                }
                if (descricao != null) {
                    CreateText(descricao, Modifier.padding(top = 8.dp), Color(0xFF388E3C), null, 14.sp) // Verde escuro para a descrição
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        adicionarProduto(quantity)
                    }) {
                        Text("Adicionar ao carrinho", color = Color.White)
                    }

                    TextButton(onClick = {
                        val currentQuantity = quantity
                        if (currentQuantity > 0) {
                            quantity = (currentQuantity - 1)
                        } else {
                            quantity = 0
                        }
                    }) {
                        Text("-", color = Color.White)
                    }

                    CreateText(
                        "$quantity",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 16.dp),
                        color = Color.Black,
                        textAlign = null,
                        fontSize = 16.sp
                    )
                    TextButton(onClick = {
                        val currentQuantity = quantity
                        quantity = (currentQuantity + 1)
                    }) {
                        Text("+", color = Color.White)
                    }
                }
            }
        }
    }
}
