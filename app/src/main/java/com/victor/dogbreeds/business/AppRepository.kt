package com.victor.dogbreeds.business

import com.victor.dogbreeds.business.daos.BreedsDao
import com.victor.dogbreeds.business.daos.UserDao
import com.victor.dogbreeds.entities.Breed
import com.victor.dogbreeds.entities.User
import io.reactivex.Single

class AppRepository(
    private val breedsDao: BreedsDao,
    private val userDao: UserDao
) : AppRepositoryContract {

    override fun insertBreed(vararg breed: Breed) = breedsDao.insert(*breed)

    override fun updateBreed(breed: Breed) = breedsDao.update(breed)

    override fun deleteAllBreeds() = breedsDao.deleteAll()

    override fun getAllBreeds(): Single<List<Breed>> = breedsDao.getAll()

    override fun insertUser(user: User) {
        userDao.insert(user)
    }

    override fun updateUser(user: User) {
        userDao.update(user)
    }

    override fun deleteUser(user: User) {
        userDao.delete(user)
    }
}