package com.example.goncalonews.app.domain.use_case

import com.example.goncalonews.app.domain.model.Noticia
import com.example.goncalonews.app.domain.repository.noticiarepository

class GetNoticiasUseCase(private val repository: noticiarepository) {
    suspend operator fun invoke(): List<Noticia>{
        return repository.getNoticias()
    }
}