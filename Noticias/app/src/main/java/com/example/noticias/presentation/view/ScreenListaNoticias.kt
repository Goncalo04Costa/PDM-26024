package com.example.noticias.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.noticias.app.domain.model.Noticia
import com.example.noticias.presentation.viewmodel.ViewModelListaNoticias
import com.example.noticias.presentation.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaNoticiasScreen(
    viewModel: ViewModelListaNoticias,
    onNoticiaSelected: (String) -> Unit
) {
    val noticiasState by viewModel.noticias.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchNoticias()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Notícias") })
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (noticiasState) {
                is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                is UiState.Success -> {
                    val noticias = (noticiasState as UiState.Success<List<Noticia>>).data
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(noticias.size) { index ->
                            val noticia = noticias[index]
                            ArticleBox(
                                article = noticia,
                                onClick = { onNoticiaSelected(noticia.id) }
                            )
                        }
                    }
                }
                is UiState.Error -> {
                    val errorMessage = (noticiasState as UiState.Error).message
                    Text(
                        text = errorMessage,
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "A carregar notícias...")
    }
}

@Composable
fun ArticleBox(
    article: Noticia,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = article.titulo,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2
            )
            Text(
                text = "Autor: ${article.autor}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp),
                maxLines = 1
            )
        }
    }
}
