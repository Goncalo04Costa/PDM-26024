package com.example.goncalostore.app.presentation.view

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.goncalostore.app.domain.model.User
import com.example.goncalostore.app.presentation.viewmodel.LoginViewModel
import com.example.goncalostore.elements.CreateText
import com.example.goncalostore.elements.CreateTextField
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel,
                navController: NavController){
    var loginUtilizador by remember {  mutableStateOf("") }
    var passwordUtilizador by remember{ mutableStateOf("")}
    var loginMessage by remember{ mutableStateOf("")}
    var loginIsNotSuccessfull by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            CreateTextField(loginUtilizador,Modifier
                .fillMaxWidth()
                .padding(16.dp)
                ,"Email:",
                valueChange ={loginUtilizador = it}, KeyboardType.Text,false)
            CreateTextField(passwordUtilizador,Modifier
                .fillMaxWidth()
                .padding(16.dp),
                "Password",
                valueChange = {passwordUtilizador=it}, KeyboardType.Password,true)
            Button(onClick ={
                if(loginUtilizador.isNullOrEmpty() || passwordUtilizador.isNullOrEmpty() ){
                    loginIsNotSuccessfull = true
                    loginMessage= "Campos não preenchidos"
                }
                else{
                    try{
                        viewModel.viewModelScope.launch {
                            val resultLogin = viewModel.signIn(loginUtilizador,passwordUtilizador)
                            if(resultLogin){
                                val verUtlz = generateUserInfo(loginUtilizador,passwordUtilizador)
                                val encodedUtlz = Uri.encode(verUtlz)
                                navController.navigate("MenuUtilizador/${encodedUtlz}")
                            }else{
                                loginIsNotSuccessfull= true
                                loginMessage="Não foi possível iniciar a sessão"
                            }
                        }
                    }catch(e:Exception){
                        Log.d("LoginScreen","Exceção:${e}")
                    }
                }
            }
            ) {
                Text("Entrar")
            }
            TextButton(onClick={
                val encodedUsername = Uri.encode(loginUtilizador)
                navController.navigate("RegistoUtilizador/${encodedUsername}")
            }){
                Text("Ainda não tem conta? Registe-se")
            }
            if(loginIsNotSuccessfull){
                CreateText(loginMessage,Modifier.fillMaxWidth().padding(16.dp),
                    Color.Black, TextAlign.Center, 32.sp)
            }
        }
    }
}

fun generateUserInfo(email:String, password:String, ):String{
    val verificarUtilizador = User(email, password)
    return Gson().toJson(verificarUtilizador)
}