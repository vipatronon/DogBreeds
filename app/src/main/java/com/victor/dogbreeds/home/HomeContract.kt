package com.victor.dogbreeds.home

interface HomeContract {
    interface View {

    }

    interface Presenter {
        fun start()
        fun destroy()
    }
}