package com.victor.dogbreeds.ui.signUp

import android.content.Context
import android.content.Intent
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.victor.dogbreeds.R
import com.victor.dogbreeds.ui.base.BaseActivity
import com.victor.dogbreeds.ui.home.HomeActivity
import com.victor.dogbreeds.util.Validators
import com.victor.dogbreeds.util.base.IValidators
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.text_field.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SignUpActivity : BaseActivity(), SignUpContract.View, IValidators by Validators() {
    override val layoutResource: Int = R.layout.activity_sign_up
    private val presenter: SignUpContract.Presenter by inject { parametersOf(this) }
    private lateinit var auth: FirebaseAuth

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }

    override fun start() {
        auth = FirebaseAuth.getInstance()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    override fun setEvents() {
        signupSignUpButton.setOnClickListener {
            signUp()
        }

        signupName.textFieldInput.addTextChangedListener(addValidator(this) { textToValidate ->
            presenter.onFinishEditFullname(textToValidate)
        })

        signupEmail.textFieldInput.addTextChangedListener(addValidator(this) { textToValidate ->
            presenter.onFinishEditEmail(textToValidate)
        })

        signupPassword.textFieldInput.addTextChangedListener(addValidator(this) { textToValidate ->
            presenter.onFinishEditPassword(textToValidate)
        })

        signupBirthdate.textFieldInput.addTextChangedListener(addValidator(this) { textToValidate ->
            presenter.onFinishEditBirthdate(textToValidate)
        })

        signupBirthdate.textFieldInput.addTextChangedListener(addDateMask(signupBirthdate))
    }

    override fun showFullnameInputError() {
        signupName.textFieldBox.error = getString(R.string.validators_messageLength)
    }

    override fun showFullnameNormalInput() {
        signupName.textFieldBox.error = ""
    }

    override fun showEmailInputError() {
        signupEmail.textFieldBox.error = getString(R.string.validators_emailError)
    }

    override fun showEmailNormalInput() {
        signupEmail.textFieldBox.error = ""
    }

    override fun showPasswordInputError() {
        signupPassword.textFieldBox.error = getString(R.string.validators_messageLength)
    }

    override fun showPasswordNormalInput() {
        signupPassword.textFieldBox.error = ""
    }

    override fun showBirthdateInputError() {
        signupBirthdate.textFieldBox.error =
            getString(R.string.validators_messageIncorrectDateFormat)
    }

    override fun showBirthdateNormalInput() {
        signupBirthdate.textFieldBox.error = ""
    }

    override fun openHomeActivity() {
        startActivity(HomeActivity.newInstance(this))
    }

    override fun displaySignUpSuccessfullyToast() {
        Toast.makeText(
            baseContext,
            getString(R.string.signup_successfullyMessage),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun displayFillFieldsToast() {
        Toast.makeText(this, getString(R.string.signup_fillMandatoryFields), Toast.LENGTH_LONG)
            .show()
    }

    override fun displayCouldNotCreateAccountToast() {
        Toast.makeText(this, getString(R.string.signup_couldNotCreateAccount), Toast.LENGTH_LONG)
            .show()
    }

    private fun signUp() {
        val email = signupEmail.textFieldInput.text.toString()
        val password = signupPassword.textFieldInput.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            displayFillFieldsToast()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    presenter.createUser(
                        signupName.textFieldInput.text.toString(),
                        signupEmail.textFieldInput.text.toString(),
                        signupBirthdate.textFieldInput.text.toString()
                    )
                } else {
                    displayCouldNotCreateAccountToast()
                }
            }
    }
}