package com.victor.dogbreeds.business

import com.victor.dogbreeds.business.models.BreedsModel
import io.reactivex.Observable

interface ApiRepositoryContract {
    fun getAllBreeds(): Observable<List<BreedsModel>>
}