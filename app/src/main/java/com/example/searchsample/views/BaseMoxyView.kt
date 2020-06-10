package com.example.searchsample.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface BaseMoxyView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun onError(message: String)
}
