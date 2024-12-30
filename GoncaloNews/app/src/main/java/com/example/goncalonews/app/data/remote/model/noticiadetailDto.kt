package com.example.goncalonews.app.data.remote.model

import com.example.goncalonews.app.domain.model.NoticiaDetalhada

data class NoticiaDetailDto(
    val title: String,
    val web_url: String,
    val published_date: String,
    val lead_paragraph: String
)


fun NoticiaDetailDto.toNoticiaDetalhada(): NoticiaDetalhada {
    return NoticiaDetalhada(
        title = this.title,
        web_url = this.web_url,
        published_date = this.published_date,
        lead_paragraph = this.lead_paragraph
    )
}
