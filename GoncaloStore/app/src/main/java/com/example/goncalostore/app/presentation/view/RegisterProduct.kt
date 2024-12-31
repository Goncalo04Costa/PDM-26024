package com.example.goncalostore.app.presentation.view

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
import com.example.goncalostore.app.presentation.viewmodel.ProdutosViewModel
import com.example.goncalostore.app.domain.model.Product
import com.example.goncalostore.elements.CreateTextField
import com.example.goncalostore.elements.CreateTextTitle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun AddProduct(viewModel: ProdutosViewModel,
               navController: NavController){
    val user = FirebaseAuth.getInstance().currentUser
    val userId = user?.uid
    var nameProduct by remember{ mutableStateOf("")}
    var priceProduct by remember{ mutableStateOf("")}
    var descriptionProduct by remember{ mutableStateOf("")}
    var isAddedText by remember{ mutableStateOf("")}
    var isAdded by remember{ mutableStateOf(false)}
    Box(modifier = Modifier
        .fillMaxSize()){
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            CreateTextTitle("Adicionar Produto",Modifier.fillMaxWidth().padding(16.dp),
                Color.Black, 32.sp)
            CreateTextField(nameProduct,Modifier.fillMaxWidth().padding(16.dp),
                "Nome Produto", valueChange = {nameProduct=it},
                KeyboardType.Text,false)
            CreateTextField(priceProduct,Modifier.fillMaxWidth().padding(16.dp),
                "Preco Produto", valueChange = {priceProduct=it},
                KeyboardType.Text,false)
            CreateTextField(descriptionProduct,Modifier.fillMaxWidth().padding(16.dp),
                "Descricao Produto", valueChange = {descriptionProduct=it},
                KeyboardType.Text,false)

            Button(onClick = {
                val newProduct = generateObject(nameProduct,priceProduct,descriptionProduct,
                    userId)
                if(!newProduct.isDefault()){
                    Log.d("AddProductScreen","Dados produto:${newProduct}")
                    viewModel.viewModelScope.launch {
                        try{
                            val result = viewModel.AddProduct(newProduct)
                            Log.d("AddProductScreen","Produto adicionado com sucesso:${result}")
                            isAddedText ="Produto adicionado com sucesso"
                            isAdded = true
                        }catch(e:Exception){
                            isAddedText = "Produto não adicionado."
                            Log.d("AddProductScreen","Ocorreu um erro:${e}")
                        }
                    }
                }else{
                    Log.d("AddProductScreen","Erro ao inserir o produto.")
                    isAddedText = "Produto não adicionado."
                }
            }){
                Text("Adicionar produto")
            }
            if(isAdded){
                Text(isAddedText)
            }else{
                Text(isAddedText)
            }
            Button(onClick = {
                navController.navigate("ProdutosScreen")
            }){
                Text("Voltar")
            }
        }
    }
}


fun generateObject(name:String, price:String, description:String, uid:String?=""):Product{
    return if(name!=null || price!=null || description!=null ||uid!=null){
        try{
            val priceInt = price.toInt()
            val newProduct = Product(name,priceInt,description,uid)
            newProduct
        }catch(e:Exception){
            Log.d("AddProductScreen","Ocorreu um erro :${e}")
            Product("", 0, "", "")
        }
    }
    else{
        Log.d("AddProductScreen","Ocorreu um erro")
        Product("", 0, "", "")
    }
}