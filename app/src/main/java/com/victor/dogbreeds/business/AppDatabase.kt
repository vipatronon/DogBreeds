package com.victor.dogbreeds.business

import androidx.room.Database
import androidx.room.RoomDatabase
import com.victor.dogbreeds.business.daos.BreedsDao
import com.victor.dogbreeds.entities.Breed

@Database(
    entities = [
        Breed::class
    ],
    version = 4
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun breedsDao(): BreedsDao
}