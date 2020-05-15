package com.victor.dogbreeds.ui.signUp

import com.victor.dogbreeds.util.Validators
import com.victor.dogbreeds.util.base.IValidators

class SignUpPresenter(
    private val view: SignUpContract.View
) : SignUpContract.Presenter,
    IValidators by Validators() {

    private var validFullname = false
    private var validEmail = false
    private var validPassword = false
    private var validBirthdate = false

    override fun onFinishEditFullname(textToValidate: String) {
        validFullname = validateTextLength(textToValidate, 5)

        if (validFullname) {
            view.showFullnameNormalInput()
        } else {
            view.showFullnameInputError()
        }
    }

    override fun onFinishEditEmail(textToValidate: String) {
        validEmail = validateTextEmail(textToValidate)

        if (validEmail) {
            view.showEmailNormalInput()
        } else {
            view.showEmailInputError()
        }
    }

    override fun onFinishEditPassword(textToValidate: String) {
        validPassword = validateTextLength(textToValidate, 5)

        if (validPassword) {
            view.showPasswordNormalInput()
        } else {
            view.showPasswordInputError()
        }
    }

    override fun onFinishEditBirthdate(textToValidate: String) {
        validBirthdate = validateTextBirthDate(textToValidate)

        if (validBirthdate) {
            view.showBirthdateNormalInput()
        } else {
            view.showBirthdateInputError()
        }
    }

    override fun signupUser() {
        if (validFullname &&
            validEmail &&
            validPassword &&
            validBirthdate
        ) {
            view.openHomeActivity()
        } else {
            view.displayWarningToastMessage()
        }
    }
}