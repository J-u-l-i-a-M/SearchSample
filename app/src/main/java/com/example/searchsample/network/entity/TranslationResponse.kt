package com.example.searchsample.network.entity

import com.google.gson.annotations.SerializedName

data class TranslationResponse(
    @SerializedName("text" ) val text: String?,
    @SerializedName("note" ) val note: String?
)
