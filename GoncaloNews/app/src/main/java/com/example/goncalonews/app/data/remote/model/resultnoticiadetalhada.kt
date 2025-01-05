package com.example.goncalonews.app.data.remote.model

import com.example.goncalonews.app.domain.model.NoticiaDetalhada
import com.google.gson.annotations.SerializedName

data class resultnoticiadetalhada (
    @SerializedName("response")
    val response: Response
)

data class Response(
    @SerializedName("docs")
    val docs: List<NoticiaDetailDto>
)