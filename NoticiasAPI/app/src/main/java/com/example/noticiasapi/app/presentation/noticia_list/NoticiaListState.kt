package com.example.noticiasapi.app.presentation.noticia_list

import com.example.noticiasapi.app.domain.model.Noticia

sealed class NoticiaListState {
    object Loading : NoticiaListState()
    data class Success(val noticias: List<Noticia>) : NoticiaListState()
    data class Error(val message: String) : NoticiaListState()
}
