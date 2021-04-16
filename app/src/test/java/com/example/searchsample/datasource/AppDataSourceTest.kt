package com.example.searchsample.datasource

import com.example.searchsample.entity.Word
import org.junit.Test

import org.junit.Assert.*

class AppDataSourceTest {
    private val dataStore = AppDataSource()

    @Test
    fun isSameSearch() {
        val search = "Word"
        val anotherSearch = "Word two"
        dataStore.putSearch(search, listOf())
        assertTrue(dataStore.isSameSearch(search))
        assertFalse(dataStore.isSameSearch(anotherSearch))
    }

    @Test
    fun getLastWords() {
        val words = listOf(Word(1, "", listOf()))
        dataStore.putSearch("", words)
        val lastWords = dataStore.getLastWords()
        assertTrue(words == lastWords)
    }
}