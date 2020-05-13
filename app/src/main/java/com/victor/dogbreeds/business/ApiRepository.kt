package com.victor.dogbreeds.business

import com.victor.dogbreeds.business.models.AllBreedsVO
import io.reactivex.Observable

class ApiRepository(
    private val api: Api
) : ApiRepositoryContract {
    override fun getAllBreeds(): Observable<AllBreedsVO> = api.getAllBreeds()
}