package com.victor.dogbreeds.di

import com.victor.dogbreeds.breedDetails.BreedDetailsContract
import com.victor.dogbreeds.breedDetails.BreedDetailsPresenter
import com.victor.dogbreeds.business.Api
import com.victor.dogbreeds.business.ApiRepository
import com.victor.dogbreeds.business.ApiRepositoryContract
import com.victor.dogbreeds.editProfile.EditProfileContract
import com.victor.dogbreeds.editProfile.EditProfilePresenter
import com.victor.dogbreeds.home.HomeContract
import com.victor.dogbreeds.home.HomePresenter
import com.victor.dogbreeds.signIn.SignInContract
import com.victor.dogbreeds.signIn.SignInPresenter
import com.victor.dogbreeds.signUp.SignUpContract
import com.victor.dogbreeds.signUp.SignUpPresenter
import com.victor.dogbreeds.splash.SplashContract
import com.victor.dogbreeds.splash.SplashPresenter
import org.koin.dsl.module
import retrofit2.Retrofit

object AppModules {
    val instance = module {
        factory<Api> { get<Retrofit>().create(Api::class.java) }

        single<ApiRepositoryContract> {ApiRepository(get())}

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
            HomePresenter(view, get())
        }

        factory<EditProfileContract.Presenter> { (view: EditProfileContract.View) ->
            EditProfilePresenter(view)
        }

        factory<BreedDetailsContract.Presenter> { (view: BreedDetailsContract.View) ->
            BreedDetailsPresenter(view)
        }
    }
}