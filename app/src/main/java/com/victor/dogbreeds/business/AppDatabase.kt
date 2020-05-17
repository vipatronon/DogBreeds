package com.victor.dogbreeds.business

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.victor.dogbreeds.business.daos.BreedsDao
import com.victor.dogbreeds.business.daos.UserDao
import com.victor.dogbreeds.entities.Breed
import com.victor.dogbreeds.entities.User

@Database(
    entities = [
        User::class,
        Breed::class
    ],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun breedsDao(): BreedsDao
    abstract fun userDao(): UserDao
}