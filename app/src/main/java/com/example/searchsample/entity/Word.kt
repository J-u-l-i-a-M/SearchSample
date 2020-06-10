package com.example.searchsample.entity

import android.os.Parcelable
import com.bignerdranch.expandablerecyclerview.model.Parent
import com.example.searchsample.network.entity.WordResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
// слово
data class Word(
    val id: Int,
    val text: String,
    val meanings: List<Meaning>
) : Parcelable, Parent<Meaning> {

    override fun getChildList(): List<Meaning> = meanings

    override fun isInitiallyExpanded() = true

    constructor(response: WordResponse) : this(
        response.id,
        response.text ?: "",
        response.meanings?.map { Meaning(it) } ?: listOf()
    )
}
