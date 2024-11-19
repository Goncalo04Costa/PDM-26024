package com.example.noticiasapi.app.domain.use_case

import com.example.noticiasapi.app.domain.model.Noticia
import com.example.noticiasapi.app.domain.repository.NoticiaRepository


class GetNoticiasUseCase (private  val repository: NoticiaRepository){
    suspend operator fun invoke(): List<Noticia> {
        return repository.getNoticias()
    }
}