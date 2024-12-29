package com.example.goncalonews.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goncalonews.app.data.remote.api.RetrofitInstance
import com.example.goncalonews.app.data.repository.noticiarepository.NoticiaRepositoryImpl
import com.example.goncalonews.app.domain.model.NoticiaDetail
import com.example.goncalonews.app.domain.use_case.GetNoticiaDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NoticiaDetailViewModel : ViewModel() {

    private val api = RetrofitInstance.api
    private val repository = NoticiaRepositoryImpl(api)
    private val getNoticiaDetailUseCase = GetNoticiaDetailUseCase(repository)

    // MutableStateFlow para armazenar o detalhe da notícia
    val noticiaDetail = MutableStateFlow<NoticiaDetail?>(null)

    // Função para buscar o detalhe da notícia
    fun fetchNoticiaDetail(noticiaId: String) {
        // Lançando a coroutine no escopo do ViewModel
        viewModelScope.launch {
            try {
                // Buscando os detalhes da notícia
                noticiaDetail.value = getNoticiaDetailUseCase(noticiaId)
            } catch (e: Exception) {
                // Em caso de erro, definindo o valor como null ou algum valor padrão
                noticiaDetail.value = null
                // Opcional: Aqui você pode adicionar logs ou mensagens de erro específicas
                e.printStackTrace()
            }
        }
    }
}
