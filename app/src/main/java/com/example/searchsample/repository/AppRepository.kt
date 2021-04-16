package com.example.searchsample.repository

import com.example.searchsample.datasource.AppDataSource
import com.example.searchsample.entity.MeaningFull
import com.example.searchsample.entity.Word
import com.example.searchsample.network.SearchApiService
import com.example.searchsample.network.exception.UIStringException
import io.reactivex.Single
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val dataSource: AppDataSource,
    private val service: SearchApiService
) {
    // загрузка данных
    fun loadData(word: String): Single<List<Word>> =
        if (dataSource.isSameSearch(word))
            Single.just(dataSource.getLastWords())
        else
            service.getWords(word)
                .map { it.map(::Word) }
                .doOnSuccess { dataSource.putSearch(word, it) }

    fun loadMeaning(meaningId: Int) =
        service.getMeanings(meaningId.toString())
            .map {
                it.map(::MeaningFull).firstOrNull { meaningId == it.id }
                    ?: throw UIStringException("Произошло что-то очень плохое")
            }
}