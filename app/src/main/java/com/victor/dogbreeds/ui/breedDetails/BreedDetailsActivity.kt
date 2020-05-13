package com.victor.dogbreeds.ui.breedDetails

import android.content.Context
import android.content.Intent
import com.victor.dogbreeds.R
import com.victor.dogbreeds.ui.base.BaseActivity

class BreedDetailsActivity: BaseActivity(), BreedDetailsContract.View {
    override val layoutResource: Int = R.layout.activity_breed_details

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, BreedDetailsActivity::class.java)
        }
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setEvents() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}