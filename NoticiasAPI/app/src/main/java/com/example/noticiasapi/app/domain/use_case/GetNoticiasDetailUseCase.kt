package com.example.noticiasapi.app.domain.use_case

import com.example.noticiasapi.app.domain.model.NoticiaDetail
import com.example.noticiasapi.app.domain.repository.NoticiaRepository

class GetNoticiasDetailUseCase (private val repository: NoticiaRepository) {
    suspend operator fun invoke(noticiaId: String): NoticiaDetail {
        return  repository.getNoticiaDetail(noticiaId)
    }
}