package com.example.searchsample.di.network

import com.example.searchsample.network.SearchApiService
import dagger.Component

@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    fun inject(service: SearchApiService)
}
