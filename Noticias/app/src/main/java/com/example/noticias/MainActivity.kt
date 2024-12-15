package com.example.noticias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noticias.presentation.screens.ListaNoticiasScreen
import com.example.noticias.presentation.view.NoticiaDetalhadaScreen
import com.example.noticias.presentation.viewmodel.ViewModelListaNoticias
import com.example.noticias.presentation.viewmodel.ViewModelNoticiaDetalhada
import com.example.noticias.ui.theme.NoticiasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           MainScreen()
        }
    }
}


@Composable
fun MainScreen() {
    val selectNoticiaId = remember { mutableStateOf<String?>(null) }

    if (selectNoticiaId.value == null) {
        val viewModelListaNoticias: ViewModelListaNoticias = viewModel()
        ListaNoticiasScreen(
            viewModel = viewModelListaNoticias,
            onNoticiaSelected = { noticiaId ->
                selectNoticiaId.value = noticiaId
            }
        )
    } else {
        val viewModelNoticiaDetalhada: ViewModelNoticiaDetalhada = viewModel()
        NoticiaDetalhadaScreen(
            viewModel = viewModelNoticiaDetalhada,
            noticiaId = selectNoticiaId.value!!,
            onBackPressed = {
                selectNoticiaId.value = null
            }
        )
    }
}
