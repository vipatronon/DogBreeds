package com.victor.dogbreeds.business

import com.victor.dogbreeds.business.dataClasses.AllBreedsVO
import com.victor.dogbreeds.business.dataClasses.BreedImageVO
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("breeds/list/all")
    fun getAllBreeds(): Observable<AllBreedsVO>

    @GET("breed/{masterBreed}/images/random")
    fun getMasterBreedImage(@Path("masterBreed") breed: String): Observable<BreedImageVO>

    @GET("breed/{masterBreed}/{subBreed}/images/random")
    fun getSubBreedImage(@Path("masterBreed") masterBreed: String, @Path("subBreed") subBreed: String): Observable<BreedImageVO>
}