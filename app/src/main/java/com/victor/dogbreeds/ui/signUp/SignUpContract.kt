package com.victor.dogbreeds.ui.signUp

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
    }

    interface Presenter {
        fun onFinishEditFullname(textToValidate: String)
        fun onFinishEditEmail(textToValidate: String)
        fun onFinishEditPassword(textToValidate: String)
        fun onFinishEditBirthdate(textToValidate: String)
        fun createUser(fullName: String, email: String, birthdate: String)
    }
}