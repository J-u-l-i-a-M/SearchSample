package com.example.searchsample.util

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.applySchedulersSingle(
    subscribeOn: Scheduler = Schedulers.io(),
    observeOn: Scheduler = AndroidSchedulers.mainThread(),
    unsubscribeOn: Scheduler = Schedulers.io()
): Single<T> = this.subscribeOn(subscribeOn)
    .observeOn(observeOn)
    .unsubscribeOn(unsubscribeOn)

fun <T> Single<T>.setStartTerminateWatcher(unit: (Boolean) -> Unit): Single<T> =
    this.doOnSubscribe { unit.invoke(true) }
        .doOnError { unit.invoke(false) }
        .doOnSuccess { unit.invoke(false) }
