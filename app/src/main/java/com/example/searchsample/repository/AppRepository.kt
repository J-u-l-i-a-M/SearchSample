package com.example.searchsample.repository

import com.example.searchsample.datastore.AppDataStore
import com.example.searchsample.entity.MeaningFull
import com.example.searchsample.entity.Word
import com.example.searchsample.network.SearchApiService
import com.example.searchsample.network.SearchApi
import com.example.searchsample.network.exception.UIStringException
import io.reactivex.Single
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val dataStore: AppDataStore
) {
    private val service by lazy { SearchApi.client.create(SearchApiService::class.java) }

    // загрузка данных
    fun loadData(word: String): Single<List<Word>> =
        if (dataStore.isSameSearch(word))
            Single.just(dataStore.getLastWords())
        else
            service.getWords(word)
                .map { it.map(::Word).toList() }
                .doOnSuccess { dataStore.putSearch(word, it) }

    fun loadMeaning(meaningId: Int) =
        service.getMeanings(meaningId.toString())
            .map {
                it.map(::MeaningFull).toList().firstOrNull { meaningId == it.id }
                    ?: throw UIStringException("Произошло что-то очень плохое")
            }
}