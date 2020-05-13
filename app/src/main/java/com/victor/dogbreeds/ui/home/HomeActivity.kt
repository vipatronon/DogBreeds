package com.victor.dogbreeds.ui.home

import android.content.Context
import android.content.Intent
import com.victor.dogbreeds.R
import com.victor.dogbreeds.ui.base.BaseActivity
import com.victor.dogbreeds.ui.editProfile.EditProfileActivity
import kotlinx.android.synthetic.main.activity_home.*
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
        presenter.start()
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun setEvents() {
        homeProfileButton.setOnClickListener {
            startActivity(EditProfileActivity.newInstance(this))
        }
    }
}