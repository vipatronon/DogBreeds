package com.victor.dogbreeds.ui.editProfile

import com.victor.dogbreeds.business.models.UserModel

interface EditProfileContract {
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
        fun displayCouldNotUpdateAccountToast()
        fun displayUpdatedSuccessfullyToast()
        fun requestLogout()
        fun requestLogoutToChangeUserName()
    }

    interface Presenter {
        fun start(userModel: UserModel)
        fun onFinishEditFullname(textToValidate: String)
        fun onFinishEditEmail(textToValidate: String)
        fun onFinishEditPassword(textToValidate: String)
        fun onFinishEditBirthdate(textToValidate: String)
        fun updateUser(fullName: String, email: String, password: String, birthdate: String)
    }
}