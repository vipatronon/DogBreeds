package com.victor.dogbreeds.ui.home

import com.google.firebase.firestore.FirebaseFirestore
import com.victor.dogbreeds.business.ApiRepositoryContract
import com.victor.dogbreeds.business.AppRepositoryContract
import com.victor.dogbreeds.business.FirestoreRefs
import com.victor.dogbreeds.business.models.BreedsModel
import com.victor.dogbreeds.business.models.firestore.FavoriteBreed
import com.victor.dogbreeds.entities.Breed
import com.victor.dogbreeds.util.AppUtil
import com.victor.dogbreeds.util.ConnectionType
import io.reactivex.Completable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class HomePresenter(
    private val view: HomeContract.View,
    private val apiRepositoryContract: ApiRepositoryContract,
    private val appRepositoryContract: AppRepositoryContract
) : HomeContract.Presenter {

    private val disposables = CompositeDisposable()
    private val users = FirebaseFirestore.getInstance().collection(FirestoreRefs.usersCollection)
    private lateinit var connectionType: ConnectionType

    override fun destroy() {
        disposables.clear()
    }

    override fun start(connectionType: ConnectionType) {
        this.connectionType = connectionType
    }

    override fun getAllBreeds(userId: String) {
        if (connectionType == ConnectionType.None) {
            fetchDataLocally()
        } else {
            fetchDataFromInternet(userId)
        }
    }

    private fun fetchDataLocally() {
        appRepositoryContract.getAllBreeds()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposables.add(it) }
            .subscribe(object : SingleObserver<List<Breed>> {
                override fun onSuccess(t: List<Breed>) {
                    view.setBreeds(t.map {
                        BreedsModel(
                            masterBreed = it.masterBreed,
                            subBreed = it.subBreed,
                            isFavorite = it.favorite,
                            displayName = it.displayName
                        )
                    })
                }

                override fun onSubscribe(d: Disposable) {}
                override fun onError(e: Throwable) {}
            })
    }

    private fun fetchDataFromInternet(userId: String) {
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
                                    it.displayName =
                                        "${it.subBreed.capitalize()} ${it.masterBreed.capitalize()}"
                                } else {
                                    it.displayName = it.masterBreed.capitalize()
                                }
                            }

                            val sortedList = t.sortedBy {
                                it.displayName
                            }

                            view.setBreeds(sortedList)

                            updateRoom(sortedList)
                        }

                        override fun onError(e: Throwable) {
                        }
                    })
            }
    }

    private fun updateRoom(breeds: List<BreedsModel>) {
        val x = breeds.map {
            Breed(
                id = null,
                masterBreed = it.masterBreed,
                subBreed = it.subBreed,
                displayName = it.displayName,
                favorite = it.isFavorite
            )
        }

        appRepositoryContract.deleteAllBreeds()
            .andThen(Completable.fromAction {
                appRepositoryContract.insertBreed(*x.toTypedArray())
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.single())
            .subscribe()
    }
}