package com.example.goncalostore.app.presentation.view



import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.goncalostore.app.domain.model.User
import com.example.goncalostore.app.presentation.viewmodel.LoginViewModel
import com.example.goncalostore.elements.CreateBottomButtons
import com.example.goncalostore.elements.CreateTextTitle
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson

@Composable
fun MenuUtilizador(viewModel: LoginViewModel,
                   navController: NavController,
                   utlzObject:String?){
    val user = FirebaseAuth.getInstance().currentUser
    val userId = user?.uid
    val decodedUtlz = Gson().fromJson(utlzObject,User::class.java)
    decodedUtlz.uid = userId
    Log.d("MenuUtilizadorScreen","Objeto:${decodedUtlz}")
    val nameUtlz = decodedUtlz.email.substringBefore("@gmail.com")
    Box(modifier= Modifier
        .fillMaxSize()){
        CreateTextTitle("Bem-vindo ${nameUtlz}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp,20.dp,0.dp,0.dp)
            ,Color.Black,
            32.sp)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

        }
        CreateBottomButtons(
            clickMenu = {},
            clickCarrinho={navController.navigate("CarrinhosScreen")},
            clickProdutos={navController.navigate("ProdutosScreen")
                Log.d("Teste","Clicked")},
            clickLogout = {
                try{
                    viewModel.signOut()
                    Log.d("MenuUtilizadorScreen","Logout efetuado com sucesso")
                    navController.navigate("Login")
                }catch(e:Exception){
                    Log.d("MenuUtilizadorScreen","Erro ao dar logout:${e}")
                }
            })
    }
}