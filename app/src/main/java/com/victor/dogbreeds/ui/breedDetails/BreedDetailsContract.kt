package com.victor.dogbreeds.ui.breedDetails

import com.victor.dogbreeds.business.models.BreedsModel

interface BreedDetailsContract {
    interface View {
        fun setScreenTitle(breed: BreedsModel)
        fun loadImage(url: String)
    }
    interface Presenter {
        fun start(breed: BreedsModel)
        fun onDestroy()
    }
}