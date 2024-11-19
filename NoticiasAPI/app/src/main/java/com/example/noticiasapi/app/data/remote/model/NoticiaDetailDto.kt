package com.example.noticiasapi.app.data.remote.model

import com.example.noticiasapi.app.domain.model.NoticiaDetail

data class NoticiaDetailDto(
    val id: String,
    val name: String,
    val description: String
) {
    fun toNoticiaDetail(): NoticiaDetail{
        return NoticiaDetail(id = id, name = name, description = description)
    }
}