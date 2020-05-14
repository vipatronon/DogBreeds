package com.victor.dogbreeds.ui.breedDetails

import android.util.Log
import com.victor.dogbreeds.business.ApiRepositoryContract
import com.victor.dogbreeds.business.models.BreedImageModel
import com.victor.dogbreeds.business.models.BreedsModel
import com.victor.dogbreeds.util.AppUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class BreedDetailsPresenter(
    private val view: BreedDetailsContract.View,
    private val apiRepositoryContract: ApiRepositoryContract
) : BreedDetailsContract.Presenter {

    private val disposables = CompositeDisposable()

    override fun start(breed: BreedsModel) {
        view.setScreenTitle(breed)
        getImageUrl(breed)
    }

    override fun onDestroy() {
        disposables.clear()
    }

    private fun getImageUrl(breed: BreedsModel) {

        val observable = if (breed.subBreed.isEmpty()) {
            apiRepositoryContract.getMasterBreedImage(breed.masterBreed)
        } else {
            apiRepositoryContract.getSubBreedImage(breed.masterBreed, breed.subBreed)
        }

        observable
            .compose(AppUtil.getNetworkThread())
            .doOnSubscribe { disposables.add(it) }
            .subscribe(object : DisposableObserver<BreedImageModel>() {
                override fun onComplete() {
                    Log.d("Details", "Completed")
                }

                override fun onNext(t: BreedImageModel) {
                    view.loadImage(t.imageUrl)
                }

                override fun onError(e: Throwable) {
                    Log.d("Details", "Error")
                }
            })
    }
}