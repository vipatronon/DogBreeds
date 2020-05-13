package com.victor.dogbreeds.util

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppUtil {
    companion object {
        fun <T> getNetworkThread(): ObservableTransformer<T, T>? {
            return ObservableTransformer { observable: Observable<T> ->
                observable.subscribeOn(
                    Schedulers.io()
                ).observeOn(AndroidSchedulers.mainThread())
            }
        }
    }
}