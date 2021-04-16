package com.example.searchsample.di.application

import com.example.searchsample.di.network.NetworkModule
import com.example.searchsample.ui.activity.SearchActivity
import com.example.searchsample.ui.dialog.MeaningDialog
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class])
interface AppComponent {
    fun inject(activity: SearchActivity)

    fun inject(dialog: MeaningDialog)
}
