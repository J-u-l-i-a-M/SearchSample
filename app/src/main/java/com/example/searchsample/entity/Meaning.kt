package com.example.searchsample.entity

import android.os.Parcelable
import com.example.searchsample.StringUtil
import com.example.searchsample.network.entity.MeaningResponse
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
        StringUtil.getTryUrl(response.previewUrl ?: ""),
        Translation(response.translation)
    )
}
