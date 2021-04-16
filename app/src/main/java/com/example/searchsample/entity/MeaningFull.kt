package com.example.searchsample.entity

import com.example.searchsample.network.entity.MeaningFullResponse
import com.example.searchsample.util.convertToUrl

// значение слово с дополнительным набором полей
data class MeaningFull(
    val id: Int,
    val text: String,
    val previewUrl: String,
    val translation: Translation
) {
    constructor(response: MeaningFullResponse) : this(
        response.id,
        response.text ?: "",
        (response.images?.firstOrNull()?.url ?: "").convertToUrl(),
        Translation(response.translation)
    )
}
