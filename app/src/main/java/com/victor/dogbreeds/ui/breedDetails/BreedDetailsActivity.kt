package com.victor.dogbreeds.ui.breedDetails

import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.victor.dogbreeds.R
import com.victor.dogbreeds.business.models.BreedsModel
import com.victor.dogbreeds.ui.base.BaseActivity
import com.victor.dogbreeds.ui.home.ItemBreed
import kotlinx.android.synthetic.main.activity_breed_details.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class BreedDetailsActivity:
    BaseActivity(),
    BreedDetailsContract.View{
    override val layoutResource: Int = R.layout.activity_breed_details

    companion object {
        const val BREED = "breed"

        fun newInstance(context: Context, breed: BreedsModel): Intent {
            return Intent(context, BreedDetailsActivity::class.java)
                .putExtra(BREED, breed)
        }
    }

    private var breed: BreedsModel? = null
    private val presenter: BreedDetailsContract.Presenter by inject { parametersOf(this) }

    override fun start() {
        breed = intent.getParcelableExtra(BREED)

        breed?.let {
            presenter.start(it)
        }
    }

    override fun setEvents() {
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun setScreenTitle(breed: BreedsModel) {
        breedDetailsHeader.text = breed.displayName
    }

    override fun loadImage(url: String) {
        Glide
            .with(this)
            .load(url)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(breedDetailsPicture)
    }
}