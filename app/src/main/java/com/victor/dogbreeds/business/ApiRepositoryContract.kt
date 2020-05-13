package com.victor.dogbreeds.business

import com.victor.dogbreeds.business.models.AllBreedsVO
import io.reactivex.Observable

interface ApiRepositoryContract {
    fun getAllBreeds(): Observable<AllBreedsVO>
}