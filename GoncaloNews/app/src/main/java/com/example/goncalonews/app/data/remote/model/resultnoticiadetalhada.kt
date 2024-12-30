package com.example.goncalonews.app.data.remote.model

import com.google.gson.annotations.SerializedName

data class resultnoticiadetalhada (

    @SerializedName("response.docs")
    var docs: List<NoticiaDetailDto>
)