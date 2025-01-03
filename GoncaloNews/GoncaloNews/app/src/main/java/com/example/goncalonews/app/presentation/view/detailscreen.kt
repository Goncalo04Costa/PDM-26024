package com.example.goncalonews.app.presentation.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.goncalonews.app.presentation.viewmodel.NoticeDetailViewModel

@Composable
fun NoticeDetailScreen(viewModel: NoticeDetailViewModel, weburl: String, onBackClick: () -> Unit) {

    LaunchedEffect(Unit) {
        viewModel.fetchnoticiadetalhada(weburl)
    }


    val noticiasDetalhadas by viewModel.noticiadetalhada.collectAsState()


    Log.d("testeScreen","noticias:${noticiasDetalhadas}")

Column(modifier = Modifier.padding(16.dp)) {
    Button(onClick = onBackClick, modifier = Modifier.padding(bottom = 16.dp)) {
        Text(text = "Voltar")
    }
        if (noticiasDetalhadas != null) {
            Text(text = noticiasDetalhadas!!.web_url, style = MaterialTheme.typography.headlineMedium)
            Text(text = noticiasDetalhadas!!.abstract, style = MaterialTheme.typography.bodySmall)
            Text(text = noticiasDetalhadas!!.lead_paragraph, style = MaterialTheme.typography.bodyLarge)
        } else {
            // Exibe uma mensagem de "Carregando..." caso os dados ainda não tenham sido carregados
            Text(text = "Carregando detalhes...", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
