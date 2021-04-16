package com.example.searchsample.di.application

import com.example.searchsample.datasource.AppDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @get:Provides
    @Singleton
    val dataSource: AppDataSource by lazy { AppDataSource() }
}