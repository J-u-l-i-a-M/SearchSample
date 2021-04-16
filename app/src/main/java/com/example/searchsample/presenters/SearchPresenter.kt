package com.example.searchsample.presenters

import com.example.searchsample.repository.AppRepository
import com.example.searchsample.util.applySchedulersSingle
import com.example.searchsample.util.setStartTerminateWatcher
import com.example.searchsample.views.SearchView
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class SearchPresenter @Inject constructor(
    private val repository: AppRepository
) : BaseMoxyPresenter<SearchView>() {

    fun searchWord(word: String) {
        disposable.set(
            repository.loadData(word)
                .applySchedulersSingle()
                .setStartTerminateWatcher(viewState::showWaitDialog)
                .subscribe({
                    viewState.onDataLoaded(it)
                }) {
                    it.printStackTrace()
                    viewState.onError("")
                }
        )
    }

    fun showMeaning(meaningId: Int) {
        viewState.showMeaning(meaningId)
    }

    fun hide() {
        viewState.hideDialog()
    }
}
