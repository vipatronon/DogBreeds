package com.victor.dogbreeds.ui.home

import com.victor.dogbreeds.business.models.BreedsModel
import com.victor.dogbreeds.util.ConnectionType

interface HomeContract {
    interface View {
        fun setBreeds(breeds: List<BreedsModel>)
    }

    interface Presenter {
        fun destroy()
        fun getAllBreeds(userId: String)
        fun start(connectionType: ConnectionType)
        fun favoriteBreed(id: String, breed: BreedsModel)
    }
}