package com.example.goncalonews.app.data.remote.model

import com.example.goncalonews.app.domain.model.NoticiaDetalhada

data class NoticiaDetailDto(
    val web_url: String,
    val abstract: String,
    val lead_paragraph: String
)


fun NoticiaDetailDto.toNoticiaDetalhada(): NoticiaDetalhada {
    return NoticiaDetalhada(
        web_url = this.web_url,
        abstract = this.abstract,
        lead_paragraph = this.lead_paragraph
    )
}
