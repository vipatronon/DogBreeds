package com.victor.dogbreeds.home

import android.content.Context
import android.content.Intent
import com.victor.dogbreeds.R
import com.victor.dogbreeds.base.BaseActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeActivity: BaseActivity(), HomeContract.View {
    override val layoutResource: Int = R.layout.activity_home
    private val presenter: HomeContract.Presenter by inject { parametersOf(this) }

    companion object {
        fun newInstance(context: Context): Intent{
            return Intent(context, HomeActivity::class.java)
        }
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setEvents() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}