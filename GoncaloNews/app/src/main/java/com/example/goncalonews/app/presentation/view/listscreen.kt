package com.example.goncalonews.app.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.goncalonews.app.domain.model.Noticia
import com.example.goncalonews.app.presentation.viewmodel.NoticeListViewModel


@Composable
fun NoticeListScreen(viewModel: NoticeListViewModel, onItemClick: (String) -> Unit) {
    LaunchedEffect(Unit) {
        viewModel.fetchNoticias()
    }
        val noticias by viewModel.noticias.collectAsState()

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(noticias) { noticia ->
                Text(
                    text = noticia.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable { onItemClick(noticia.web_url)},
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
