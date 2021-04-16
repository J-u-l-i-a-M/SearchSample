package com.example.searchsample.di.network

import com.example.searchsample.network.SearchApiService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Получения данных из сети
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Provides
    fun provideService(retrofit: Retrofit): SearchApiService = retrofit.create(SearchApiService::class.java)

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient = OkHttpClient()

    companion object {
        private const val BASE_URL = "https://dictionary.skyeng.ru/"
    }
}