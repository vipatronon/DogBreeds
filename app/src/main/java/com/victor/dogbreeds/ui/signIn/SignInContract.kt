package com.victor.dogbreeds.ui.signIn

interface SignInContract {
    interface View {
        fun showEmailInputError()
        fun showEmailNormalInput()
    }

    interface Presenter {
        fun start()
        fun onFinishEditEmail(textToValidate: String)
    }
}