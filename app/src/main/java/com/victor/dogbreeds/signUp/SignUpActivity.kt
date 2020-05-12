package com.victor.dogbreeds.signUp

import android.content.Context
import android.content.Intent
import com.victor.dogbreeds.R
import com.victor.dogbreeds.base.BaseActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SignUpActivity: BaseActivity(), SignUpContract.View {
    override val layoutResource: Int = R.layout.activity_sign_up
    private val presenter: SignUpContract.Presenter by inject { parametersOf(this) }

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setEvents() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}