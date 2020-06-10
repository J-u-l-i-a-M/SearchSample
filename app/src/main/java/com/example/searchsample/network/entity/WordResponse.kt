package com.example.searchsample.network.entity

import com.google.gson.annotations.SerializedName

data class WordResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("text" ) val text: String?,
    @SerializedName("meanings") val meanings: List<MeaningResponse>?
)