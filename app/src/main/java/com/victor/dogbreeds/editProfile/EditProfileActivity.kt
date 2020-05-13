package com.victor.dogbreeds.editProfile

import android.content.Context
import android.content.Intent
import com.victor.dogbreeds.R
import com.victor.dogbreeds.base.BaseActivity

class EditProfileActivity : BaseActivity(), EditProfileContract.View {
    override val layoutResource: Int = R.layout.activity_edit_profile

    companion object {
        fun newInstance (context: Context): Intent {
            return Intent(context, EditProfileActivity::class.java)
        }
    }

    override fun start() {
    }

    override fun setEvents() {
    }
}