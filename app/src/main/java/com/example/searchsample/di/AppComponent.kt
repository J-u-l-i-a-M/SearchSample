package com.example.searchsample.di

import com.example.searchsample.ui.activity.SearchActivity
import com.example.searchsample.ui.dialog.MeaningDialog
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface AppComponent {
    fun inject(activity: SearchActivity)

    fun inject(dialog: MeaningDialog)
}
