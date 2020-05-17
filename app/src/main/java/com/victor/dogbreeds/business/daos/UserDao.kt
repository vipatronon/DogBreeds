package com.victor.dogbreeds.business.daos

import androidx.room.*
import com.victor.dogbreeds.entities.User
import io.reactivex.Completable

@Dao
interface UserDao {

    @Insert
    fun insert(user: User): Completable

    @Update
    fun update(user: User): Completable

    @Delete
    fun delete(user: User): Completable
}