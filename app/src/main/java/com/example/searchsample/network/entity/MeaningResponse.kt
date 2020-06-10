package com.example.searchsample.network.entity

import com.google.gson.annotations.SerializedName

data class MeaningResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("previewUrl" ) val previewUrl: String?,
    @SerializedName("translation") val translation: TranslationResponse?
)
