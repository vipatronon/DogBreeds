package com.victor.dogbreeds.di

import androidx.room.Room
import com.victor.dogbreeds.BuildConfig
import com.victor.dogbreeds.business.AppDatabase
import com.victor.dogbreeds.business.AppRepository
import com.victor.dogbreeds.business.AppRepositoryContract
import org.koin.dsl.module

object DatabaseModules {
    val instance = module {
        single {
            Room.databaseBuilder(get(), AppDatabase::class.java, BuildConfig.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        single { get<AppDatabase>().breedsDao() }
        single { get<AppDatabase>().userDao() }

        single<AppRepositoryContract> { AppRepository(get(), get()) }
    }
}