package com.example.searchsample.entity

import android.os.Parcelable
import com.example.searchsample.network.entity.MeaningResponse
import com.example.searchsample.util.convertToUrl
import kotlinx.android.parcel.Parcelize

@Parcelize
// значение слова
data class Meaning(
    val id: Int,
    val previewUrl: String,
    val translation: Translation
) : Parcelable {
    constructor(response: MeaningResponse) : this(
        response.id,
        (response.previewUrl ?: "").convertToUrl(),
        Translation(response.translation)
    )
}
