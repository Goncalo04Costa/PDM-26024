package com.example.noticiasapi.app.presentation.noticia_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticiasapi.app.data.remote.api.RetrofitInstance
import com.example.noticiasapi.app.data.remote.repository.NoticiaRepositoryImpl
import com.example.noticiasapi.app.domain.model.Noticia
import com.example.noticiasapi.app.domain.model.NoticiaDetail
import com.example.noticiasapi.app.domain.use_case.GetNoticiasDetailUseCase
import com.example.noticiasapi.app.domain.use_case.GetNoticiasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NoticiaListViewModel : ViewModel() {
    private  val api = RetrofitInstance.api
    private  val repository = NoticiaRepositoryImpl(api)
    private  val getNoticiasUseCase = GetNoticiasUseCase(repository)

    val Noticias = MutableStateFlow<List<Noticia>>(emptyList())

    fun fetchNoticias()
    {
        viewModelScope.launch {
            try {
                Noticias.value = getNoticiasUseCase()
            } catch (e: Exception) {
                Noticias.value = emptyList()
            }
        }
    }

}

class NoticiaDetailViewModel : ViewModel() {
    private  val api = RetrofitInstance.api
    private  val repository = NoticiaRepositoryImpl(api)
    private  val getNoticiasDetailUseCase = GetNoticiasDetailUseCase(repository)

    val noticiaDetail = MutableStateFlow<NoticiaDetail?>(null)

    fun fetchNoticias(noticiaId: String)
    {
        viewModelScope.launch {
            try {
                noticiaDetail.value = getNoticiasDetailUseCase(noticiaId)
            } catch (e: Exception) {
                noticiaDetail.value = null
            }
        }
    }
}