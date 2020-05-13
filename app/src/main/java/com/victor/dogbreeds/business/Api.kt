package com.victor.dogbreeds.business

import com.victor.dogbreeds.business.models.AllBreedsVO
import io.reactivex.Observable
import retrofit2.http.GET

interface Api {
    @GET("list/all")
    fun getAllBreeds(): Observable<AllBreedsVO>
}