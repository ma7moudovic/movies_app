package com.shar2wy.moviesapp.viewModels

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by shar2wy
 * on 11/18/18.
 * Description: description goes here
 */
abstract class UseCase internal constructor(executorThreadParam: Scheduler, uiThreadParam: Scheduler) {
    protected val executorThread: Scheduler = executorThreadParam
    protected val uiThread: Scheduler = uiThreadParam
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun addObserver(observer: Disposable) {
        compositeDisposable.add(observer)
    }

    fun dispose() {
        compositeDisposable.clear()
    }
}