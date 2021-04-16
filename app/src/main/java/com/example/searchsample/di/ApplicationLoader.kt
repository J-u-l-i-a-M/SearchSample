package com.example.searchsample.di

import androidx.multidex.MultiDexApplication
import com.example.searchsample.di.application.AppModule

class ApplicationLoader : MultiDexApplication() {
    val applicationModule by lazy { AppModule() }

    init {
        instance = this
    }

    companion object {
        lateinit var instance: ApplicationLoader
            private set
    }
}