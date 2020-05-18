package com.victor.dogbreeds.ui.home

import com.victor.dogbreeds.business.models.BreedsModel
import com.victor.dogbreeds.business.models.UserModel
import com.victor.dogbreeds.util.ConnectionType

interface HomeContract {
    interface View {
        fun setBreeds(breeds: List<BreedsModel>)
        fun showErrorToastMessage()
        fun setUserModel(userModel: UserModel)
        fun startShimmer()
        fun stopShimmer()
    }

    interface Presenter {
        fun destroy()
        fun getAllBreeds(userId: String)
        fun start(connectionType: ConnectionType)
        fun favoriteBreed(id: String, breed: BreedsModel)
    }
}