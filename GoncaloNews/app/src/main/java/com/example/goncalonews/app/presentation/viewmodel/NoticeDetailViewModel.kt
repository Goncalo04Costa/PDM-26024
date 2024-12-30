package com.example.goncalonews.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goncalonews.app.data.remote.api.RetrofitInstance
import com.example.goncalonews.app.data.remote.repository.NoticiaRepositoryImpl
import com.example.goncalonews.app.domain.model.NoticiaDetalhada
import com.example.goncalonews.app.domain.use_case.GetNoticiasDetalhadasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NoticeDetailViewModel : ViewModel() {
    private val api = RetrofitInstance.api
    private val repository = NoticiaRepositoryImpl(api)
    private val getNoticiasDetalhadasUseCase = GetNoticiasDetalhadasUseCase(repository)

    val noticiadetalhada = MutableStateFlow<NoticiaDetalhada?>(null)

    fun fetchnoticiadetalhada(noticiatitle: String) {
        viewModelScope.launch {
            try {
                noticiadetalhada.value = getNoticiasDetalhadasUseCase(noticiatitle)
            } catch (e: Exception) {
                noticiadetalhada.value = null
            }
        }
    }
}