package com.victor.dogbreeds.ui.home

import com.victor.dogbreeds.business.ApiRepositoryContract
import com.victor.dogbreeds.business.models.BreedsModel
import com.victor.dogbreeds.util.AppUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class HomePresenter(
    private val view: HomeContract.View,
    private val apiRepositoryContract: ApiRepositoryContract
) : HomeContract.Presenter {

    private val disposables = CompositeDisposable()

    override fun destroy() {
        disposables.clear()
    }

    override fun getAllBreeds() {
        apiRepositoryContract.getAllBreeds()
            .compose(AppUtil.getNetworkThread())
            .doOnSubscribe { disposables.add(it) }
            .subscribe(object : DisposableObserver<List<BreedsModel>>() {
                override fun onComplete() {
                }

                override fun onNext(t: List<BreedsModel>) {
                    view.setBreeds(t)
                }

                override fun onError(e: Throwable) {
                }
            })
    }
}