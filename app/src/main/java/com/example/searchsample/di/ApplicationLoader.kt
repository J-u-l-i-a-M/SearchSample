package com.example.searchsample.di

import androidx.multidex.MultiDexApplication

class ApplicationLoader : MultiDexApplication() {
    val applicationModule by lazy { ApplicationModule() }

    init {
        instance = this
    }

    companion object {
        lateinit var instance: ApplicationLoader
            private set
    }
}