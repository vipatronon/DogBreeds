package com.victor.dogbreeds.ui.splash

interface SplashContract {
    interface View{
        fun sendToHome()
        fun sendToSignIn()
    }
    interface Presenter {
        fun start()
        fun checkSignedInStatus()
    }
}