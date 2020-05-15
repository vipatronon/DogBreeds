package com.victor.dogbreeds.ui.home

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.victor.dogbreeds.R
import com.victor.dogbreeds.business.models.BreedsModel
import com.victor.dogbreeds.ui.base.BaseActivity
import com.victor.dogbreeds.ui.breedDetails.BreedDetailsActivity
import com.victor.dogbreeds.ui.editProfile.EditProfileActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeActivity : BaseActivity(),
    HomeContract.View,
    ItemBreed.ItemBreedCallback {
    override val layoutResource: Int = R.layout.activity_home
    private val presenter: HomeContract.Presenter by inject { parametersOf(this) }

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
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

    override fun setBreeds(breeds: List<BreedsModel>) {
        homeBreedsList.apply {
            adapter = BreedsAdapter(breeds, this@HomeActivity)
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun openBreedDetails(breed: BreedsModel) {
        startActivity(BreedDetailsActivity.newInstance(this, breed))
    }

    override fun favoriteBreed(breed: BreedsModel) {
        breed.isFavorite = !breed.isFavorite
    }
}