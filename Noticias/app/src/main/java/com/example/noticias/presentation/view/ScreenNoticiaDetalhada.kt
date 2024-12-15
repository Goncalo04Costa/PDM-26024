package com.example.noticias.presentation.view



import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.noticias.presentation.viewmodel.ViewModelNoticiaDetalhada

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoticiaDetalhadaScreen(
    viewModel: ViewModelNoticiaDetalhada,
    noticiaId: String,
    onBackPressed: () -> Unit
) {
    val noticiaDetalhadaState by viewModel.noticiasDetalhadas.collectAsState()

    // Fetch noticia details
    LaunchedEffect(key1 = noticiaId) {
        viewModel.fetchNoticiaDetail(noticiaId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes da Notícia") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (noticiaDetalhadaState) {
                null -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                else -> {
                    val noticia = noticiaDetalhadaState!!
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = noticia.titulo,
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = noticia.descricao,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
