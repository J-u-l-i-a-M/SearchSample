package com.example.searchsample.views

import com.example.searchsample.entity.Word
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SearchView : BaseMoxyView {
    fun onDataLoaded(words: List<Word>)

    fun onSearchLoaded(search: String)

    fun showWaitDialog(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMeaning(meaningId: Int)

    fun hideDialog()
}