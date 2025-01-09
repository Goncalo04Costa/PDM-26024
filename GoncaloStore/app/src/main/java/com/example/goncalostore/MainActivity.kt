package com.example.goncalostore


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.goncalostore.app.navigation.AppNavigation
import com.example.goncalostore.app.navigation.Routes
import com.example.goncalostore.app.ui.GoncaloStoreTheme


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoncaloStoreTheme {
                AppNavigation(startDestination = Routes.LOGIN)
            }
        }
    }

}