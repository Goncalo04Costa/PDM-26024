package com.example.carrinhodecompras.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carrinhodecompras.Classes.Produto
import com.example.carrinhodecompras.UIElements.CreateBottomButtons
import com.example.carrinhodecompras.UIElements.CreateText
import com.example.carrinhodecompras.viewmodel.MenuUtilizadorViewModel
import com.example.carrinhodecompras.viewmodel.ProdutosViewModel

@Composable
fun MenuProdutos(viewModelProdutos: ProdutosViewModel,
                 viewModelUtilizador: MenuUtilizadorViewModel,
                 navController: NavController,
) {
    val listProducts = remember { mutableStateOf(emptyList<Produto>()) }
    val isLoading = remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        try {
            val products = viewModelProdutos.FetchProducts()
            listProducts.value = products
            isLoading.value = false
        } catch (e: Exception) {
            Log.d("ProdutosScreen", "Erro:${e}")
        }
    }
    Log.d("ProdutosScreen", "Produtos: ${listProducts}")
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (isLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (listProducts.value.isEmpty()) {
                    item {
                        CreateText(
                            "Não existem produtos",
                            modifier = Modifier.fillMaxSize(),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            32.sp
                        )
                    }
                } else {
                    items(listProducts.value) { item ->
                        ProductItemBox(
                            nome = item.nome,
                            descricao = item.descricao,
                            preco = item.preco.toString()
                        )
                    }
                }
            }
        }

        TextButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 72.dp)
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(12.dp)
                ),
            onClick = {
                navController.navigate("AddProduct")
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Adicionar Produto",
                    fontSize = 24.sp
                )
                Text(
                    text = "+",
                    color = Color.White,
                    fontSize = 24.sp
                )
            }
        }

        CreateBottomButtons(
            clickMenu = {},
            clickCarrinho = { navController.navigate("CarrinhosScreen") },
            clickProdutos = { navController.navigate(this) },
            clickLogout = {
                try {
                    viewModelUtilizador.signOut()
                    Log.d("MenuUtilizadorScreen", "Logout efetuado com sucesso")
                    navController.navigate("Login")
                } catch (e: Exception) {
                    Log.d("MenuUtilizadorScreen", "Erro ao dar logout:${e}")
                }
            }
        )
    }

}


@Composable
fun ProductItemBox(nome:String?, descricao:String?, preco:String?){
    if(!nome.isNullOrEmpty() || !descricao.isNullOrEmpty() || !preco.isNullOrEmpty()){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(1.dp, Color.Black,shape = RoundedCornerShape(8.dp))
                .background(Color.LightGray,shape = RoundedCornerShape(8.dp))
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
                        CreateText(nome,Modifier.align(Alignment.Top),Color.Black,null,16.sp)
                    }
                    if (preco != null) {
                        CreateText(preco+"€",Modifier.align(Alignment.CenterVertically),Color.Black,
                            TextAlign.End,32.sp)
                    }
                }
                if (descricao != null) {
                    CreateText(descricao,Modifier.padding(top = 8.dp),Color.Cyan,null,16.sp)
                }
            }
        }
    }
}