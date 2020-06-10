package com.example.searchsample.datastore

import com.example.searchsample.entity.Word

data class AppDataStore(
    private var lastSearch: String = "",
    private val lastWords: MutableList<Word> = mutableListOf()
) {
    fun putSearch(search: String, words: List<Word>) {
        lastSearch = search
        lastWords.clear()
        lastWords.addAll(words)
    }

    fun isSameSearch(search: String) = lastSearch == search

    fun getLastWords() = lastWords.toList()
}