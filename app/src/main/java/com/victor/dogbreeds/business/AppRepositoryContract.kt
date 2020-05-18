package com.victor.dogbreeds.business

import com.victor.dogbreeds.entities.Breed
import io.reactivex.Completable
import io.reactivex.Single

interface AppRepositoryContract {

    fun insertBreed(vararg breed: Breed)

    fun updateBreed(breed: Breed)

    fun deleteAllBreeds(): Completable

    fun getAllBreeds(): Single<List<Breed>>
}