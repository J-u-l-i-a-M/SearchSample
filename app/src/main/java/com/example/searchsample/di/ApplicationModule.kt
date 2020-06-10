package com.example.searchsample.di

import com.example.searchsample.datastore.AppDataStore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {
    @get:Provides
    @Singleton
    val dataString: AppDataStore by lazy { AppDataStore() }
}