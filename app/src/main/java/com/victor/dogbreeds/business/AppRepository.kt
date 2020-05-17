package com.victor.dogbreeds.business

import com.victor.dogbreeds.business.daos.BreedsDao
import com.victor.dogbreeds.entities.Breed
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppRepository(
    private val breedsDao: BreedsDao
) : AppRepositoryContract {

    override fun insertBreed(vararg breed: Breed) {
        breedsDao.insert(*breed)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun updateBreed(breed: Breed) = breedsDao.update(breed)

    override fun deleteAllBreeds() = breedsDao.deleteAll()

    override fun getAllBreeds(): Single<List<Breed>> = breedsDao.getAll()
}