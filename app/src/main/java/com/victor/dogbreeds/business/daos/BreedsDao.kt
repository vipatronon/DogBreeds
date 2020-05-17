package com.victor.dogbreeds.business.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.victor.dogbreeds.entities.Breed
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface BreedsDao {
    @Insert
    fun insert(vararg breed: Breed): Completable

    @Update
    fun update(breed: Breed): Completable

    @Query("DELETE FROM tb_breeds")
    fun deleteAll(): Completable

    @Query("SELECT * FROM tb_breeds ORDER BY displayName")
    fun getAll(): Single<List<Breed>>
}