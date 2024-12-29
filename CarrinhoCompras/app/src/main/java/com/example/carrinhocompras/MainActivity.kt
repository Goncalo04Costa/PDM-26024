package com.example.carrinhocompras



import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.simulateHotReload
import com.example.carrinhocompras.navigation.Navigation
import com.example.carrinhodecompras.ui.theme.CarrinhoDeComprasTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarrinhoDeComprasTheme {
                Navigation()
            }
        }
    }

}


