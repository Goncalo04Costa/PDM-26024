package com.example.noticiasapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.noticiasapi.app.presentation.noticia_list.NoticiaDetailViewModel
import com.example.noticiasapi.app.presentation.noticia_list.NoticiaListViewModel
import com.example.noticiasapi.ui.theme.NoticiasAPITheme


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Passa o viewModel como parâmetro para o Composable

        }
    }
}


@Composable
fun MainScreen() {
    /*
    println("")
    */
}
