package com.example.carrinhocompras.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.carrinhocompras.view.AddCarrinho
import com.example.carrinhocompras.view.AddProduct
import com.example.carrinhocompras.view.CartScreen
import com.example.carrinhocompras.view.LoginScreen
import com.example.carrinhocompras.view.MenuProdutos
import com.example.carrinhocompras.view.RegistUser

@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Login"){
        composable("Login") {
            LoginScreen(viewModel(),navController)
        }
        composable("MenuUtilizador/{utlzObject}",
            arguments = listOf(navArgument("utlzObject"){
                type = NavType.StringType
                nullable = false
            })
        ){ navBackStackEntry->
            val utlzEncoded =navBackStackEntry.arguments?.getString("utlzObject")
            val utlzDecoded =utlzEncoded?.let { Uri.decode(it) }
        }
        composable("RegistoUtilizador/{loginUtilizador}"){navBackStackEntry->
            val usernameEncoded = navBackStackEntry.arguments?.getString("loginUtilizador")
            if(usernameEncoded !=null){
                val usernameDecoded = usernameEncoded?.let { Uri.decode(it) }
                RegistUser(viewModel(),navController,usernameDecoded)
            }else{
                RegistUser(viewModel(),navController,"")
            }
        }
        composable("ProdutosScreen"){
            MenuProdutos(viewModel(),viewModel(),navController,)
        }
        composable("AddProduct"){
            AddProduct(viewModel(),navController)
        }
        composable("AddCarrinho"){
            AddCarrinho(viewModel(), viewModel(),navController)
        }
        composable("CarrinhosScreen"){
            CartScreen(viewModel(),viewModel(),navController)
        }
    }
}