package com.victor.dogbreeds.ui.splash

import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.victor.dogbreeds.R
import com.victor.dogbreeds.ui.base.BaseActivity
import com.victor.dogbreeds.ui.home.HomeActivity
import com.victor.dogbreeds.ui.signIn.SignInActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SplashActivity : BaseActivity(), SplashContract.View {
    override val layoutResource: Int = R.layout.activity_splash
    private val presenter: SplashContract.Presenter by inject { parametersOf(this) }

    override fun start() {
        Handler().postDelayed({
            presenter.start()
            presenter.checkSignedInStatus()
        }, 750)
    }

    override fun setEvents() {
    }

    override fun sendToHome() {
        startActivity(HomeActivity.newInstance(this))
    }

    override fun sendToSignIn() {
        startActivity(SignInActivity.newInstance(this))
    }
}