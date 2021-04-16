package com.example.searchsample.datasource

import com.example.searchsample.entity.Word

data class AppDataSource(
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