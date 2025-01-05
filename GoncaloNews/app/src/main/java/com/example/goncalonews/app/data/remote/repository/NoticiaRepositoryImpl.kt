package com.example.goncalonews.app.data.remote.repository

import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.goncalonews.app.data.remote.api.NYtimesAPI
import com.example.goncalonews.app.data.remote.model.toNoticia
import com.example.goncalonews.app.data.remote.model.toNoticiaDetalhada
import com.example.goncalonews.app.domain.model.Noticia
import com.example.goncalonews.app.domain.model.NoticiaDetalhada
import com.example.goncalonews.app.domain.repository.noticiarepository
import com.example.goncalonews.app.presentation.viewmodel.NoticeDetailViewModel
class NoticiaRepositoryImpl(private val api: NYtimesAPI) : noticiarepository {

    override suspend fun getNoticias(): List<Noticia> {
        Log.d("NoticiaRepositoryImpl","API call: ${api.getNoticias()}")

        return api.getNoticias().results.map { it.toNoticia() }  // Mapeia para o modelo de domínio Noticia
    }


    override suspend fun getNoticiasDetalhadas(weburl: String): NoticiaDetalhada {
        val apiCall = "web_url:\"${weburl}\""

        return api.getNoticiaDetalhada(fq = apiCall).response.docs.first().toNoticiaDetalhada()

    }
}
