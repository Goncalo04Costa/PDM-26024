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

    private val api = RetrofitInstance.api
    private val repository = NoticiaRepositoryImpl(api)
    private val getNoticiasUseCase = GetNoticiasUseCase(repository)


    private val _noticias = MutableStateFlow<List<Noticia>>(emptyList())
    val noticias: StateFlow<List<Noticia>> = _noticias


    fun fetchNoticias() {
        viewModelScope.launch {
            try {
                val noticiasResponse = getNoticiasUseCase()
                _noticias.value = noticiasResponse
            } catch (e: Exception) {
                _noticias.value = emptyList()
            }
        }
    }
}
