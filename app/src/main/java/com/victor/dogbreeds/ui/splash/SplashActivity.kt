package com.victor.dogbreeds.ui.splash

import android.os.Handler
import com.victor.dogbreeds.R
import com.victor.dogbreeds.ui.base.BaseActivity
import com.victor.dogbreeds.ui.signIn.SignInActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SplashActivity : BaseActivity(), SplashContract.View {
    override val layoutResource: Int = R.layout.activity_splash
    private val presenter: SplashContract.Presenter by inject { parametersOf(this) }

    override fun start() {
        presenter.checkSignedInStatus()

        Handler().postDelayed({
            startActivity(SignInActivity.newInstance(this))
        }, 750)
    }

    override fun setEvents() {
    }

    override fun sendToHome() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendToSignIn() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}