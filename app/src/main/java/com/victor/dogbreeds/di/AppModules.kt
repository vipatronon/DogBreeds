package com.victor.dogbreeds.di

import com.victor.dogbreeds.signIn.SignInContract
import com.victor.dogbreeds.signIn.SignInPresenter
import com.victor.dogbreeds.signUp.SignUpContract
import com.victor.dogbreeds.signUp.SignUpPresenter
import com.victor.dogbreeds.splash.SplashContract
import com.victor.dogbreeds.splash.SplashPresenter
import org.koin.dsl.module

object AppModules {
    val instance = module {
        factory<SplashContract.Presenter> { (view: SplashContract.View) ->
            SplashPresenter(view)
        }

        factory<SignInContract.Presenter> { (view: SignInContract.View) ->
            SignInPresenter(view)
        }

        factory <SignUpContract.Presenter> { (view: SignUpContract.View) ->
            SignUpPresenter(view)
        }
    }
}