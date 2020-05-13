package com.victor.dogbreeds.ui.signIn

import android.content.Context
import android.content.Intent
import com.victor.dogbreeds.R
import com.victor.dogbreeds.ui.base.BaseActivity
import com.victor.dogbreeds.ui.home.HomeActivity
import com.victor.dogbreeds.ui.signUp.SignUpActivity
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SignInActivity : BaseActivity(), SignInContract.View {
    override val layoutResource: Int = R.layout.activity_sign_in
    private val presenter: SignInContract.Presenter by inject { parametersOf(this) }

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, SignInActivity::class.java)
        }
    }

    override fun start() {
    }

    override fun setEvents() {
        signinSignupButton.setOnClickListener {
            startActivity(SignUpActivity.newInstance(this))
        }

        signinSignInButton.setOnClickListener {
            startActivity(HomeActivity.newInstance(this))
        }
    }
}