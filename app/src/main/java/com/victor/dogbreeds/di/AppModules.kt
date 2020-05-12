package com.victor.dogbreeds.di

import com.victor.dogbreeds.splash.SplashContract
import com.victor.dogbreeds.splash.SplashPresenter
import org.koin.dsl.module

object AppModules {
    val instance = module {
        factory<SplashContract.Presenter> { (view: SplashContract.View) ->
            SplashPresenter(view)
        }
    }
}