package com.example.noticias.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticias.app.data.remote.api.RetrofitInstance
import com.example.noticias.app.data.remote.model.NoticiaDetalhadaDto
import com.example.noticias.app.data.repository.RepositorioNoticiasImpl
import com.example.noticias.app.domain.model.NoticiaDetalhada
import com.example.noticias.app.domain.use_case.GetNoticiasDetalhadasUseCase
import com.example.noticias.ui.theme.NoticiasTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ViewModelNoticiaDetalhada  : ViewModel() {
    private  val api = RetrofitInstance.api
    private  val repository = RepositorioNoticiasImpl(api)
    private val getNoticiasDetalhadasUseCase = GetNoticiasDetalhadasUseCase(repository)

    val noticiasDetalhadas = MutableStateFlow<NoticiaDetalhada?>(null)

    fun fetchNoticiaDetail(noticiaId: String){
        viewModelScope.launch {
            try {
                noticiasDetalhadas.value = getNoticiasDetalhadasUseCase(noticiaId)
            } catch (e: Exception) {
                noticiasDetalhadas.value = null
            }
        }
    }

}