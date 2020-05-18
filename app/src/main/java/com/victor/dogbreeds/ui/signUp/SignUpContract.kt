package com.victor.dogbreeds.ui.signUp

import android.app.Activity

interface SignUpContract {
    interface View {
        fun showFullnameInputError()
        fun showFullnameNormalInput()
        fun showEmailInputError()
        fun showEmailNormalInput()
        fun showPasswordInputError()
        fun showPasswordNormalInput()
        fun showBirthdateInputError()
        fun showBirthdateNormalInput()
        fun openHomeActivity()
        fun displaySignUpSuccessfullyToast()
        fun displayFillFieldsToast()
        fun displayCouldNotCreateAccountToast()
        fun hideButton()
        fun showShimmer()
        fun hideShimmer()
        fun showButton()
    }

    interface Presenter {
        fun start()
        fun onFinishEditFullname(textToValidate: String)
        fun onFinishEditEmail(textToValidate: String)
        fun onFinishEditPassword(textToValidate: String)
        fun onFinishEditBirthdate(textToValidate: String)
        fun signUp(
            activity: Activity,
            fullname: String,
            email: String,
            password: String,
            birthdate: String
        )
    }
}