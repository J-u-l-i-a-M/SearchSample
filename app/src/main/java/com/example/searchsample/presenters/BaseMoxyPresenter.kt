package com.example.searchsample.presenters

import com.example.searchsample.views.BaseMoxyView
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject
import moxy.MvpPresenter
import rx.subscriptions.CompositeSubscription

abstract class BaseMoxyPresenter<View : BaseMoxyView> : MvpPresenter<View>() {
    // возможно пригодится :)
}
