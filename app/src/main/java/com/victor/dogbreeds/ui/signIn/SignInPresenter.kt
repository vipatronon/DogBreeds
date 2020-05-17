package com.victor.dogbreeds.ui.signIn

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.victor.dogbreeds.util.Validators
import com.victor.dogbreeds.util.base.IValidators

class SignInPresenter(
    private val view: SignInContract.View
) : SignInContract.Presenter,
    IValidators by Validators() {
    private lateinit var auth: FirebaseAuth

    override fun start() {
        auth = FirebaseAuth.getInstance()
    }

    override fun onFinishEditEmail(textToValidate: String) {
        if (validateTextEmail(textToValidate)) {
            view.showEmailNormalInput()
        } else {
            view.showEmailInputError()
        }
    }

    override fun signIn(activity: Activity, email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            view.showMandatoryFieldsToast()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    view.openHomeActivity()
                } else {
                    view.showSignInErrorToast()
                }
            }
    }

    override fun resetPassword(email: String) {
        if (email.isNotEmpty()) {
            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    view.showCheckEmailToast()
                }
                .addOnFailureListener {
                    view.showCouldNotResetPasswordToast()
                }
        } else {
            view.showFillEmailToast()
        }
    }
}