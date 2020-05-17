package com.victor.dogbreeds.di

import com.victor.dogbreeds.business.Api
import com.victor.dogbreeds.business.ApiRepository
import com.victor.dogbreeds.business.ApiRepositoryContract
import com.victor.dogbreeds.ui.breedDetails.BreedDetailsContract
import com.victor.dogbreeds.ui.breedDetails.BreedDetailsPresenter
import com.victor.dogbreeds.ui.editProfile.EditProfileContract
import com.victor.dogbreeds.ui.editProfile.EditProfilePresenter
import com.victor.dogbreeds.ui.home.HomeContract
import com.victor.dogbreeds.ui.home.HomePresenter
import com.victor.dogbreeds.ui.signIn.SignInContract
import com.victor.dogbreeds.ui.signIn.SignInPresenter
import com.victor.dogbreeds.ui.signUp.SignUpContract
import com.victor.dogbreeds.ui.signUp.SignUpPresenter
import com.victor.dogbreeds.ui.splash.SplashContract
import com.victor.dogbreeds.ui.splash.SplashPresenter
import org.koin.dsl.module
import retrofit2.Retrofit

object AppModules {
    val instance = module {
        factory<Api> { get<Retrofit>().create(Api::class.java) }

        single<ApiRepositoryContract> { ApiRepository(get()) }

        factory<SplashContract.Presenter> { (view: SplashContract.View) ->
            SplashPresenter(view)
        }

        factory<SignInContract.Presenter> { (view: SignInContract.View) ->
            SignInPresenter(view)
        }

        factory<SignUpContract.Presenter> { (view: SignUpContract.View) ->
            SignUpPresenter(view)
        }

        factory<HomeContract.Presenter> { (view: HomeContract.View) ->
            HomePresenter(view, get(), get())
        }

        factory<EditProfileContract.Presenter> { (view: EditProfileContract.View) ->
            EditProfilePresenter(view)
        }

        factory<BreedDetailsContract.Presenter> { (view: BreedDetailsContract.View) ->
            BreedDetailsPresenter(view, get())
        }
    }
}