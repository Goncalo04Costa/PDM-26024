package com.example.carrinhodecompras.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.carrinhodecompras.view.*
import com.example.carrinhodecompras.viewmodel.CarrinhosViewModel
import com.example.carrinhodecompras.viewmodel.MenuUtilizadorViewModel
import com.example.carrinhodecompras.viewmodel.ProdutosViewModel
import com.example.carrinhodecompras.viewmodel.RegistoUtilizadorViewModel


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Login") {
        composable("Login") {
            val loginViewModel: MenuUtilizadorViewModel = viewModel()
            LoginScreen(loginViewModel, navController)
        }

        composable(
            "MenuUtilizador/{utlzObject}",
            arguments = listOf(navArgument("utlzObject") {
                type = NavType.StringType
                nullable = false
            })
        ) { navBackStackEntry ->
            val utlzEncoded = navBackStackEntry.arguments?.getString("utlzObject")
            val utlzDecoded = utlzEncoded?.let { Uri.decode(it) }

            if (!utlzDecoded.isNullOrEmpty()) {
                val menuViewModel: MenuUtilizadorViewModel = viewModel()
                MenuUtilizador(menuViewModel, navController, utlzDecoded)
            } else {
                Log.e("Navigation", "Invalid or missing user object")

                // Caso o objeto seja inválido ou nulo, você pode redirecionar para a tela de Login ou exibir uma mensagem
                navController.navigate("Login") // Redirecionando para a tela de login
            }
        }

        composable("RegistoUtilizador/{loginUtilizador}") { navBackStackEntry ->
            val usernameEncoded = navBackStackEntry.arguments?.getString("loginUtilizador")
            val usernameDecoded = usernameEncoded?.let { Uri.decode(it) } ?: ""
            val registerViewModel: RegistoUtilizadorViewModel = viewModel()
            RegistoUtilizador(registerViewModel, navController, usernameDecoded)
        }

        composable("ProdutosScreen") {
            val productViewModel: ProdutosViewModel = viewModel()
            MenuProdutos(productViewModel, navController)
        }

        composable("AddProduct") {
            val productViewModel: ProdutosViewModel = viewModel()
            AddProduct(productViewModel, navController)
        }

        composable("AddCarrinho") {
            val cartViewModel: CarrinhosViewModel = viewModel()
            AddCarrinho(cartViewModel, navController)
        }

        composable("CarrinhosScreen") {
            val cartViewModel: CarrinhosViewModel = viewModel()
            CarrinhosScreen(cartViewModel, navController)
        }
    }
}
