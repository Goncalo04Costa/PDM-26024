package com.example.goncalonews.app.data.remote.model

import com.google.gson.annotations.SerializedName

data class resultnoticia(
    @SerializedName("results")
    val results: List<NoticiaDto>
)