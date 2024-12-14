package com.example.carrinhodecompras.view



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
import androidx.compose.material3.Button
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.carrinhodecompras.Classes.Carrinho
import com.example.carrinhodecompras.UIElements.CreateBottomButtons
import com.example.carrinhodecompras.UIElements.CreateText
import com.example.carrinhodecompras.viewmodel.CarrinhosViewModel
import com.example.carrinhodecompras.viewmodel.MenuUtilizadorViewModel
import kotlinx.coroutines.launch

@Composable
fun CarrinhosScreen(viewModelCarrinho: CarrinhosViewModel,
                    viewModelUtilizador: MenuUtilizadorViewModel,
                    navController: NavController){

    val listCarrinhos = remember{ mutableStateOf(emptyList<Carrinho>())}
    val isLoading = remember{ mutableStateOf(true)}

    LaunchedEffect(Unit) {
        try{
            val carrinhos = viewModelCarrinho.FetchCarrinhos()
            listCarrinhos.value = carrinhos
            isLoading.value = false
        }catch(e:Exception){
            Log.d("CarrinhosScreen","Erro:${e}")
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading.value) {
            } else if (listCarrinhos.value.isEmpty()) {
                item {
                    CreateText("Não existem carrinhos", Modifier.fillMaxSize(),
                        Color.Black, TextAlign.Center, 32.sp)
                }
            } else {
                items(listCarrinhos.value) { item ->
                    CarrinhoItemBox(
                        item.donoCarrinho,
                        item
                    )
                }
            }
        }
        TextButton(modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .padding(bottom = 72.dp)
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            ),
            onClick = {
                navController.navigate("AddCarrinho")
            }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Adicionar Carrinho",
                    fontSize = 24.sp)
                Text("+",
                    color = Color.White,
                    fontSize = 24.sp)
            }
        }
        CreateBottomButtons(
            clickMenu={},
            clickCarrinho={},
            clickProdutos = {navController.navigate(this)},
            clickLogout = {
                try{
                    viewModelUtilizador.signOut()
                    Log.d("MenuUtilizadorScreen","Logout efetuado com sucesso")
                    navController.navigate("Login")
                }catch(e:Exception){
                    Log.d("MenuUtilizadorScreen","Erro ao dar logout:${e}")
                }
            }
        )
    }
}


@Composable
fun CarrinhoItemBox(dono:String?,carrinho:Carrinho){
    var isSheetOpen by remember{ mutableStateOf(false)}
    if(!dono.isNullOrEmpty()){
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
                    if (dono != null) {
                        CreateText(dono,Modifier.align(Alignment.Top),Color.Black,null,16.sp)
                    }
                }
                Button(onClick = {isSheetOpen = true
                }){Text("Ver produtos")}
                if(isSheetOpen){
                    Dialog(onDismissRequest = {isSheetOpen=false}) {
                        Box(modifier = Modifier
                            .fillMaxSize()){
                            LazyColumn(modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                if (carrinho.listaProdutos.isEmpty()) {
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
                                    items(carrinho.listaProdutos) { item ->
                                        ProductItemBox(
                                            nome = item.produto?.nome,
                                            descricao = item.produto?.descricao,
                                            preco = item.produto?.preco.toString()
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}