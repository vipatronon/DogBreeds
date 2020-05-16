package com.victor.dogbreeds.ui.home

import com.victor.dogbreeds.business.models.BreedsModel

interface HomeContract {
    interface View {
        fun setBreeds(breeds: List<BreedsModel>)
    }

    interface Presenter {
        fun destroy()
        fun getAllBreeds(userId: String)
    }
}