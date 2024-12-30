package com.example.goncalonews.app.domain.use_case

import com.example.goncalonews.app.domain.model.NoticiaDetalhada
import com.example.goncalonews.app.domain.repository.NoticiaRepository

class GetNoticiasDetalhadasUseCase(private val repository: NoticiaRepository){
    suspend operator fun invoke (weburl:String): NoticiaDetalhada {
        return  repository.getNoticiasDetalhadas((weburl))
    }
}