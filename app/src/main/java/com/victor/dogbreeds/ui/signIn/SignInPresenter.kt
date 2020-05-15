package com.victor.dogbreeds.ui.signIn

import com.victor.dogbreeds.util.Validators
import com.victor.dogbreeds.util.base.IValidators

class SignInPresenter(
    private val view: SignInContract.View
) : SignInContract.Presenter,
    IValidators by Validators() {

    override fun start() {

    }

    override fun onFinishEditEmail(textToValidate: String) {
        if (validateTextEmail(textToValidate)){
            view.showEmailNormalInput()
        } else {
            view.showEmailInputError()
        }
    }
}