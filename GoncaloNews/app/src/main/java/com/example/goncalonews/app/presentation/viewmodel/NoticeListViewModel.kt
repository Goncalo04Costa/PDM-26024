package com.example.goncalonews.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goncalonews.app.data.remote.api.RetrofitInstance
import com.example.goncalonews.app.data.remote.repository.NoticiaRepositoryImpl
import com.example.goncalonews.app.domain.model.Noticia
import com.example.goncalonews.app.domain.use_case.GetNoticiasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoticeListViewModel : ViewModel() {
    // Inicializando o Retrofit e o repositório
    private val api = RetrofitInstance.api
    private val repository = NoticiaRepositoryImpl(api)
    private val getNoticiasUseCase = GetNoticiasUseCase(repository)

    // Expondo um StateFlow imutável para ser observado pela UI
    private val _noticias = MutableStateFlow<List<Noticia>>(emptyList())
    val noticias: StateFlow<List<Noticia>> = _noticias

    // Função para buscar as notícias da API
    fun fetchNoticias() {
        viewModelScope.launch {
            try {
                val noticiasResponse = getNoticiasUseCase() // Chamada à API
                _noticias.value = noticiasResponse // Atualiza o estado com as notícias
            } catch (e: Exception) {
                _noticias.value = emptyList() // Caso haja erro, mantém a lista vazia
            }
        }
    }
}
