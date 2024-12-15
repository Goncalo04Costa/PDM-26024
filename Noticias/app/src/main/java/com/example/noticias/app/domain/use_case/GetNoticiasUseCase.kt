package com.example.noticias.app.domain.use_case

import com.example.noticias.app.domain.model.Noticia
import com.example.noticias.app.domain.model.NoticiaDetalhada
import com.example.noticias.app.domain.repository.RepositorioNoticias
import com.example.noticias.presentation.viewmodel.UiState


class GetNoticiasUseCase (private  val repository: RepositorioNoticias) {
    suspend operator fun invoke(): UiState<List<Noticia>> {
        return repository.getNoticias()
    }
}

class GetNoticiasDetalhadasUseCase(private val repository: RepositorioNoticias) {
    suspend operator  fun invoke(noticiaId: String) : NoticiaDetalhada {
        return  repository.getNoticiasDetail(noticiaId)
    }
}