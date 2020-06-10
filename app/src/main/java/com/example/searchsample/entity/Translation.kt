package com.example.searchsample.entity

import android.os.Parcelable
import com.example.searchsample.network.entity.TranslationResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
// перевод слова
class Translation(
    val text: String,
    val note: String
) : Parcelable {
    constructor(response: TranslationResponse?) : this(
        response?.text ?: "",
        response?.note ?: ""
    )
}
