package com.example.goncalonews.app.domain.use_case

import android.util.Log
import com.example.goncalonews.app.domain.model.NoticiaDetalhada
import com.example.goncalonews.app.domain.repository.noticiarepository

class getnoticiasdetalhadasusecase(private val repository: noticiarepository){
    suspend operator fun invoke (weburl:String): NoticiaDetalhada {
        return  repository.getNoticiasDetalhadas(weburl)
    }
}