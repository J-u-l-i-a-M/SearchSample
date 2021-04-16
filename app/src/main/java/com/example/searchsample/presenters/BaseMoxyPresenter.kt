package com.example.searchsample.presenters

import com.example.searchsample.views.BaseMoxyView
import io.reactivex.disposables.SerialDisposable
import moxy.MvpPresenter

abstract class BaseMoxyPresenter<View : BaseMoxyView> : MvpPresenter<View>() {
    protected val disposable = SerialDisposable()

    override fun onDestroy() {
        super.onDestroy()
        if (disposable.isDisposed) disposable.dispose()
    }
}
