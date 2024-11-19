package com.example.noticiasapi.app.data.remote.model

import com.google.gson.annotations.SerializedName
import com.example.noticiasapi.app.domain.model.Noticia

data class ResponseAPI(
    @SerializedName("data")
    val data: List<NoticiaDto>,
    @SerializedName("meta")
    val meta: Meta?
)
data class ResponseApiDetail(
    @SerializedName("data")
    val data: List<NoticiaDetailDto>,
    @SerializedName("meta")
    val meta: Meta?
)
data class Meta(
    @SerializedName("found")
    val found: Int?,
    @SerializedName("limit")
    val limit: Int?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("returned")
    val returned: Int?
)