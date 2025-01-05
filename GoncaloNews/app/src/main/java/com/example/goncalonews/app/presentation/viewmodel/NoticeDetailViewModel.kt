package com.example.goncalonews.app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goncalonews.app.data.remote.api.RetrofitInstance
import com.example.goncalonews.app.data.remote.repository.NoticiaRepositoryImpl
import com.example.goncalonews.app.domain.model.NoticiaDetalhada
import com.example.goncalonews.app.domain.use_case.getnoticiasdetalhadasusecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NoticeDetailViewModel : ViewModel() {
    private val api = RetrofitInstance.api
    private val repository = NoticiaRepositoryImpl(api)
    private val getDetalhadas = getnoticiasdetalhadasusecase(repository)

    val noticiadetalhada = MutableStateFlow<NoticiaDetalhada?>(value = null)


    fun fetchnoticiadetalhada(weburl: String) {
        viewModelScope.launch {
            try {
                noticiadetalhada.value = getDetalhadas(weburl)
            } catch (e: Exception) {
                noticiadetalhada.value = null
            }
        }
    }
}