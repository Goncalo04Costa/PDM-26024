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
import com.example.loja.classes.Carrinho
import com.example.loja.viewmodel.CarrinhosViewModel
import com.example.loja.viewmodel.LoginViewModel
import com.example.loja.elementos.CreateTextTitle
import com.example.loja.elementos.CreateBottomButtons
import com.example.loja.elementos.CreateText

import kotlinx.coroutines.launch

@Composable
fun CarrinhosScreen(viewModelCarrinho: CarrinhosViewModel,
                    viewModelUtilizador: LoginViewModel,
                    navController: NavController){

    val listCarrinhos = remember{ mutableStateOf(emptyList<Carrinho>())}
    val isLoading = remember{ mutableStateOf(true)}

    LaunchedEffect(Unit) {
        try{
            val carrinhos = viewModelCarrinho.ProcuraCarrinhos()
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


