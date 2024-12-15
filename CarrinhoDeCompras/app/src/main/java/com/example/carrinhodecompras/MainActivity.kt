package com.example.carrinhodecompras.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carrinhodecompras.presentation.view.CartScreen
import com.example.carrinhodecompras.presentation.view.ProductListScreen
import com.example.carrinhodecompras.presentation.view.RegisterScreen
import com.example.carrinhodecompras.presentation.viewmodel.AuthViewModel
import com.example.carrinhodecompras.presentation.viewmodel.CartViewModel
import com.example.carrinhodecompras.presentation.viewmodel.ProductViewModel
import com.example.carrinhodecompras.repositorio.AuthRepository
import com.example.carrinhodecompras.repositorio.CartRepository
import com.example.carrinhodecompras.repositorio.ProductRepository
import com.example.carrinhodecompras.ui.theme.CarrinhoDeComprasTheme

class MainActivity : ComponentActivity() {
    private val authRepository = AuthRepository()
    private val cartRepository = CartRepository()
    private val productRepository = ProductRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarrinhoDeComprasTheme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavigationGraph(navController = navController)
                }
            }
        }
    }

    @Composable
    fun NavigationGraph(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "register") {
            // Passando os ViewModels com os repositórios para as telas
            composable("register") {
                RegisterScreen(
                    authViewModel = AuthViewModel(authRepository),
                    onRegisterSuccess = {
                        navController.navigate("product_list") {
                            // Evita que o usuário volte para a tela de registro
                            popUpTo("register") { inclusive = true }
                        }
                    },
                    onNavigateToLogin = { navController.navigate("login") }
                )
            }
            composable("login") {
                // A tela de login será aqui (não foi implementada no seu código)
            }
            composable("product_list") {
                ProductListScreen(
                    productViewModel = ProductViewModel(productRepository),
                    onNavigateToCart = { navController.navigate("cart") },
                    onAddToCart = { product ->
                        // Adiciona ao carrinho
                    }
                )
            }
            composable("cart") {
                CartScreen(
                    cartViewModel = CartViewModel(cartRepository),
                    onCheckout = {
                        // Implementar checkout
                    }
                )
            }
        }
    }
}
