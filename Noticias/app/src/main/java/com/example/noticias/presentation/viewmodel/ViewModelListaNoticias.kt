package com.example.noticias.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticias.app.data.remote.api.RetrofitInstance
import com.example.noticias.app.data.repository.RepositorioNoticiasImpl
import com.example.noticias.app.domain.model.Noticia
import com.example.noticias.app.domain.use_case.GetNoticiasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class ViewModelListaNoticias : ViewModel() {
    private  val api = RetrofitInstance.api
    private  val repository = RepositorioNoticiasImpl(api)
    private val getNoticiasUseCase = GetNoticiasUseCase(repository)

    val noticias = MutableStateFlow<UiState<List<Noticia>>>(UiState.Loading)

    fun fetchNoticias() {
        viewModelScope.launch {
            try {
                noticias.value = getNoticiasUseCase()
            } catch ( e: Exception) {
                noticias.value = UiState.Error("Erro ao carregar")
            }
        }
    }

}