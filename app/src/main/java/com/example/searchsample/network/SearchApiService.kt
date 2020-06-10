package com.example.searchsample.network

import com.example.searchsample.network.entity.MeaningFullResponse
import com.example.searchsample.network.entity.WordResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {
    @GET("api/public/v1/words/search")
    // поиск слов для перевода
    fun getWords(@Query("search") word: String): Single<List<WordResponse>?>

    @GET("/api/public/v1/meanings")
    // поиск значений слов
    fun getMeanings(@Query("ids") ids: String): Single<List<MeaningFullResponse>?>
}