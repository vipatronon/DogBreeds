package com.victor.dogbreeds.business

import com.victor.dogbreeds.business.models.BreedImageModel
import com.victor.dogbreeds.business.models.BreedsModel
import io.reactivex.Observable

interface ApiRepositoryContract {
    fun getAllBreeds(): Observable<List<BreedsModel>>
    fun getMasterBreedImage(masterBreed: String): Observable<BreedImageModel>
    fun getSubBreedImage(masterBreed: String, subBreed: String): Observable<BreedImageModel>
}