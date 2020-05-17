package com.victor.dogbreeds.business

import com.victor.dogbreeds.entities.Breed
import com.victor.dogbreeds.entities.User
import io.reactivex.Completable
import io.reactivex.Single

interface AppRepositoryContract {

    fun insertBreed(vararg breed: Breed): Completable

    fun updateBreed(breed: Breed): Completable

    fun deleteAllBreeds(): Completable

    fun getAllBreeds(): Single<List<Breed>>

    fun insertUser(user: User)

    fun updateUser(user: User)

    fun deleteUser(user: User)
}