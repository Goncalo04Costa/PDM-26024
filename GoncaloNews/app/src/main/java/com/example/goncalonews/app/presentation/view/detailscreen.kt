package com.example.goncalonews.app.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.goncalonews.app.domain.model.NoticiaDetalhada
import com.example.goncalonews.app.presentation.viewmodel.NoticeDetailViewModel

@Composable
fun NoticeDetailScreen(viewModel: NoticeDetailViewModel, weburl: String, onBackClick: () -> Unit) {
    // Iniciar a chamada à API quando o título da notícia mudar
    LaunchedEffect(weburl) {
        viewModel.fetchnoticiadetalhada(weburl)
    }

    // Coletando o estado atual da lista de notícias detalhadas
    val noticiasDetalhadas = viewModel.noticiadetalhada.collectAsState().value

    // Pegando o primeiro item da lista, se existir
    val noticia = noticiasDetalhadas

    // Layout da tela de detalhes da notícia
    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = onBackClick, modifier = Modifier.padding(bottom = 16.dp)) {
            Text(text = "Voltar")
        }

        // Exibindo os dados da notícia
        if (noticia != null) {
            Text(text = noticia.title, style = MaterialTheme.typography.headlineMedium)
            Text(text = noticia.published_date, style = MaterialTheme.typography.bodySmall)
            Text(text = noticia.lead_paragraph, style = MaterialTheme.typography.bodyLarge)
        } else {
            // Exibe uma mensagem de "Carregando..." caso os dados ainda não tenham sido carregados
            Text(text = "Carregando detalhes...", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
