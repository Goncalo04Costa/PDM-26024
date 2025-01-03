package com.example.goncalonews.app.data.remote.model

import com.example.goncalonews.app.domain.model.NoticiaDetalhada
import com.google.gson.annotations.SerializedName

data class NoticiaDetailDto(
    @SerializedName("abstract")
    val abstract: String,
    @SerializedName("web_url")
    val webUrl: String,
    @SerializedName("snippet")
    val snippet: String,
    @SerializedName("lead_paragraph")
    val leadParagraph: String,
    @SerializedName("source")
    val source: String
)


fun NoticiaDetailDto.toNoticiaDetalhada(): NoticiaDetalhada {
    return NoticiaDetalhada(
        web_url = this.webUrl,
        abstract = this.abstract,
        lead_paragraph = this.leadParagraph
    )
}
