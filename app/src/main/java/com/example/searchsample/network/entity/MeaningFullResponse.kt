package com.example.searchsample.network.entity

import com.google.gson.annotations.SerializedName

data class MeaningFullResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("text") val text: String?,
    @SerializedName("images" ) val images: List<ImageResponse>?,
    @SerializedName("translation") val translation: TranslationResponse?
)
