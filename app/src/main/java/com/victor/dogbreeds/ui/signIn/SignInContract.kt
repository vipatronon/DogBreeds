package com.victor.dogbreeds.ui.signIn

import android.app.Activity

interface SignInContract {
    interface View {
        fun showEmailInputError()
        fun showEmailNormalInput()
        fun showMandatoryFieldsToast()
        fun openHomeActivity()
        fun showSignInErrorToast()
        fun showCheckEmailToast()
        fun showCouldNotResetPasswordToast()
        fun showFillEmailToast()
        fun hideSignInButton()
        fun showShimmer()
        fun hideShimmer()
        fun showSignInButton()
    }

    interface Presenter {
        fun start()
        fun onFinishEditEmail(textToValidate: String)
        fun signIn(activity: Activity, email: String, password: String)
        fun resetPassword(email: String)
    }
}