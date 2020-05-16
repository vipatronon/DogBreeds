package com.victor.dogbreeds.ui.home

import com.google.firebase.firestore.FirebaseFirestore
import com.victor.dogbreeds.business.ApiRepositoryContract
import com.victor.dogbreeds.business.FirestoreRefs
import com.victor.dogbreeds.business.models.BreedsModel
import com.victor.dogbreeds.business.models.FavoriteBreed
import com.victor.dogbreeds.util.AppUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class HomePresenter(
    private val view: HomeContract.View,
    private val apiRepositoryContract: ApiRepositoryContract
) : HomeContract.Presenter {

    private val disposables = CompositeDisposable()
    private val users = FirebaseFirestore.getInstance().collection(FirestoreRefs.usersCollection)

    override fun destroy() {
        disposables.clear()
    }

    override fun getAllBreeds(userId: String) {
        val favoriteBreedsCollection =
            users.document(userId).collection(FirestoreRefs.favoritesCollection)

        favoriteBreedsCollection.get()
            .addOnSuccessListener { result ->

                val favoriteBreeds = result.toObjects(FavoriteBreed::class.java).map {
                    BreedsModel(
                        masterBreed = it.masterBreed!!,
                        subBreed = it.subBreed!!,
                        isFavorite = it.favorite!!
                    )
                }

                apiRepositoryContract.getAllBreeds()
                    .compose(AppUtil.getNetworkThread())
                    .doOnSubscribe { disposables.add(it) }
                    .subscribe(object : DisposableObserver<List<BreedsModel>>() {
                        override fun onComplete() {
                        }

                        override fun onNext(t: List<BreedsModel>) {
                            favoriteBreeds.forEach { favorite ->
                                t.forEach { list ->
                                    if (favorite.masterBreed == list.masterBreed &&
                                        favorite.subBreed == list.subBreed
                                    ) {
                                        list.isFavorite = favorite.isFavorite
                                    }
                                }
                            }

                            t.forEach {
                                if (it.subBreed.isNotEmpty()) {
                                    it.displayName = "${it.subBreed} ${it.masterBreed}"
                                } else {
                                    it.displayName = it.masterBreed
                                }
                            }

                            view.setBreeds(t.sortedBy {
                                it.displayName
                            })
                        }

                        override fun onError(e: Throwable) {
                        }
                    })
            }

    }
}