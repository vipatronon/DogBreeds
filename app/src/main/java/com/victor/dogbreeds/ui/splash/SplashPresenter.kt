package com.victor.dogbreeds.ui.splash

import com.google.firebase.auth.FirebaseAuth

class SplashPresenter(
    private val view: SplashContract.View
) : SplashContract.Presenter {
    private lateinit var auth: FirebaseAuth

    override fun start() {
        auth = FirebaseAuth.getInstance()
    }

    override fun checkSignedInStatus() {
        if (auth.currentUser != null) {
            view.sendToHome()
        } else {
            view.sendToSignIn()
        }
    }
}