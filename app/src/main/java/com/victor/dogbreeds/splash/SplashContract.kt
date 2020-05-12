package com.victor.dogbreeds.splash

interface SplashContract {
    interface View{
        fun sendToHome()
        fun sendToSignIn()
    }
    interface Presenter {
        fun checkSignedInStatus()
    }
}