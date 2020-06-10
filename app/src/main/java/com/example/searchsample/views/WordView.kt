package com.example.searchsample.views

import com.example.searchsample.entity.MeaningFull
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface WordView : BaseMoxyView {
    fun onDataLoaded(word: MeaningFull)
}