package com.victor.dogbreeds.ui.signIn

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.victor.dogbreeds.R
import com.victor.dogbreeds.ui.base.BaseActivity
import com.victor.dogbreeds.ui.home.HomeActivity
import com.victor.dogbreeds.ui.signUp.SignUpActivity
import com.victor.dogbreeds.util.Validators
import com.victor.dogbreeds.util.base.IValidators
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.text_field.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SignInActivity : BaseActivity(), SignInContract.View, IValidators by Validators() {
    override val layoutResource: Int = R.layout.activity_sign_in
    private val presenter: SignInContract.Presenter by inject { parametersOf(this) }

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, SignInActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun start() {
        presenter.start()
    }

    override fun setEvents() {
        signinSignupButton.setOnClickListener {
            startActivity(SignUpActivity.newInstance(this))
        }

        signinSignInButton.setOnClickListener {
            checkFieldsForSignIn()
        }

        signinEmail.textFieldInput.addTextChangedListener(addValidator(this) { textToValidate ->
            presenter.onFinishEditEmail(
                textToValidate
            )
        })
    }

    private fun checkFieldsForSignIn() {
        if (signinEmail.textFieldInput.text.toString().isNotEmpty() &&
            signinPassword.textFieldInput.text.toString().isNotEmpty()
        ) {
            startActivity(HomeActivity.newInstance(this))
        } else {
            Toast.makeText(this, getString(R.string.signin_fillMandatoryFields), Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun showEmailInputError() {
        signinEmail.textFieldBox.error = getString(R.string.validators_emailError)
    }

    override fun showEmailNormalInput() {
        signinEmail.textFieldBox.error = ""
    }
}