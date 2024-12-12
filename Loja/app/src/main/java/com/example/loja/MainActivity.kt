package com.example.loja

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.loja.ui.theme.CarrinhoDeComprasTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.loja.view.RegistoUtilizador

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // FirebaseAuth Listener
        val auth: FirebaseAuth = Firebase.auth
        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                Log.d("MainActivity", "Usuário logado: ${user.email}")
            } else {
                Log.d("MainActivity", "Nenhum usuário logado.")
            }
        }

        // Configuração da interface do usuário
        setContent {
            CarrinhoDeComprasTheme {
                val navController = rememberNavController()

                // Tela principal com botão para navegar para o registro
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Tela Principal")

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = {
                            navController.navigate("register") // Navegar para a tela de registro
                        }) {
                            Text("Ir para Registro")
                        }
                    }

                    // Navegação para a tela de registro
                    NavHost(navController, startDestination = "main") {
                        composable("main") {
                            // Tela principal aqui
                        }
                        composable("register") {
                            // Tela de Registro
                            RegistoUtilizador(viewModel = viewModel(), navController = navController)
                        }
                    }
                }
            }
        }
    }
}
