package com.victor.dogbreeds.business.daos

import androidx.room.*
import com.victor.dogbreeds.entities.Breed
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface BreedsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg breed: Breed): Completable

    @Update
    fun update(breed: Breed): Completable

    @Query("DELETE FROM tb_breeds")
    fun deleteAll(): Completable

    @Query("SELECT * FROM tb_breeds ORDER BY displayName")
    fun getAll(): Single<List<Breed>>
}