package com.victor.dogbreeds.signIn

import android.content.Context
import android.content.Intent
import com.victor.dogbreeds.R
import com.victor.dogbreeds.base.BaseActivity
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
    }
}