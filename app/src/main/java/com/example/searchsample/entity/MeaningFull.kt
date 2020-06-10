package com.example.searchsample.entity

import com.example.searchsample.StringUtil
import com.example.searchsample.network.entity.MeaningFullResponse

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
        StringUtil.getTryUrl(response.images?.firstOrNull()?.url ?: ""),
        Translation(response.translation)
    )
}
