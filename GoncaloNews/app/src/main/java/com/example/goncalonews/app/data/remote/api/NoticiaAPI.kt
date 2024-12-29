package com.example.goncalonews.app.data.remote.api

import com.example.goncalonews.app.data.remote.model.NoticiaDetailDto
import com.example.goncalonews.app.data.remote.model.NoticiaDto
import com.example.goncalonews.app.domain.model.Noticia
import com.example.goncalonews.app.domain.model.NoticiaDetail
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Response

// RetrofitInstance para configuração do Retrofit
object RetrofitInstance {
    private const val BASE_URL = "https://api.sportmonks.com/v3/"
    public const val API_TOKEN = "9vp7ZyWSyMdlyV9LSAmySppTBPKnCmFQFVu1O1mMtuqd6yI1t9scBgpaENpx"

    val api: SportmonksApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SportmonksApi::class.java)
    }
}

interface SportmonksApi {

    // Método para buscar a lista de notícias
    @GET("noticias")
    suspend fun getNoticias(): List<NoticiaDto>

    // Método para buscar os detalhes de uma notícia específica
    @GET("noticias/{id}")
    suspend fun getNoticiaDetail(@Path("id") id: String): NoticiaDetailDto
}

// Response wrapper para as notícias
data class NoticiasResponse(
    val data: List<Noticia>  // Lista de notícias
)

// Response wrapper para os detalhes da notícia
data class NoticiaDetailResponse(
    val data: NoticiaDetail  // Detalhes completos da notícia
)
