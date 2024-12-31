package com.example.goncalostore.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateBottomButtons(
    clickMenu:()->Unit={},
    clickCarrinho:()->Unit={},
    clickProdutos:()->Unit={},
    clickLogout:()->Unit={},

    ){
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)){
        Row(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly){
            TextButton(onClick = {clickMenu()}){
                Text("Menu")
            }
            TextButton(onClick = {clickCarrinho()}){
                Text("Carrinhos")
            }
            TextButton(onClick = {clickProdutos()}){
                Text("Produtos")
            }
            TextButton(onClick = {clickLogout()}){
                Text("Logout")
            }
        }
    }
}