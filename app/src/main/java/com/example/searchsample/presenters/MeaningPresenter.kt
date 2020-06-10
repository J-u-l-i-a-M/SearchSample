package com.example.searchsample.presenters

import android.annotation.SuppressLint
import com.example.searchsample.repository.AppRepository
import com.example.searchsample.util.applySchedulersSingle
import moxy.InjectViewState
import javax.inject.Inject
import com.example.searchsample.views.WordView

@InjectViewState
class MeaningPresenter @Inject constructor(
    private val repository: AppRepository
) : BaseMoxyPresenter<WordView>() {

    @SuppressLint("CheckResult")
    fun loadWord(wordId: Int) {
        repository.loadMeaning(wordId)
            .applySchedulersSingle()
            .subscribe(
                {
                    viewState.onDataLoaded(it)
                },
                {
                    viewState.onError("")
                    it.printStackTrace()
                }
            )
    }
}