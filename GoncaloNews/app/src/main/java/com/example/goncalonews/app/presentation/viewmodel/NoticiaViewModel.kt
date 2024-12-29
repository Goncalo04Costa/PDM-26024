package com.example.goncalonews.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goncalonews.app.data.remote.api.RetrofitInstance
import com.example.goncalonews.app.data.repository.noticiarepository.NoticiaRepositoryImpl
import com.example.goncalonews.app.domain.model.Noticia
import com.example.goncalonews.app.domain.repository.NoticiaRepository
import com.example.goncalonews.app.domain.use_case.GetNoticiasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoticiaViewModel : ViewModel() {
    // Instância do Retrofit API
    private val api = RetrofitInstance.api
    // Repositório de notícias
    private val repository = NoticiaRepositoryImpl(api)
    // Caso de uso para obter notícias
    private val getNoticiasUseCase = GetNoticiasUseCase(repository)

    // MutableStateFlow para armazenar a lista de notícias
    private val _noticias = MutableStateFlow<List<Noticia>>(emptyList())

    // Exposição imutável para a lista de notícias
    val noticias: StateFlow<List<Noticia>> = _noticias

    // Função para buscar as notícias
    fun fetchNoticias() {
        viewModelScope.launch {
            try {

                _noticias.value = getNoticiasUseCase()
            } catch (e: Exception) {

                _noticias.value = emptyList()
                e.printStackTrace()
            }
        }
    }
}
