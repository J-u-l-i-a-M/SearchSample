package com.example.searchsample.presenters

import com.example.searchsample.repository.AppRepository
import com.example.searchsample.util.applySchedulersSingle
import com.example.searchsample.views.WordView
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class MeaningPresenter @Inject constructor(
    private val repository: AppRepository
) : BaseMoxyPresenter<WordView>() {

    fun loadWord(wordId: Int) {
        disposable.set(repository.loadMeaning(wordId)
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
        )
    }
}