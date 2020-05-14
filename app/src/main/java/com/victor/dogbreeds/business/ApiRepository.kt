package com.victor.dogbreeds.business

import com.victor.dogbreeds.business.models.BreedImageModel
import com.victor.dogbreeds.business.models.BreedsModel
import io.reactivex.Observable

class ApiRepository(
    private val api: Api
) : ApiRepositoryContract {
    override fun getAllBreeds(): Observable<List<BreedsModel>> = api.getAllBreeds().map { vo ->
        val breeds = mutableListOf<BreedsModel>()
        if (vo.status == "success") {
            vo.message.forEach { breed ->
                if (breed.subBreeds.isNullOrEmpty()) {
                    breeds.add(BreedsModel(breed.breedName, ""))
                } else {
                    breed.subBreeds.forEach { subBreed ->
                        breeds.add(BreedsModel(breed.breedName, subBreed))
                    }
                }
            }
        }
        breeds
    }

    override fun getMasterBreedImage(masterBreed: String): Observable<BreedImageModel> =
        api.getMasterBreedImage(masterBreed).map { vo ->
            if (vo.status == "success") {
                BreedImageModel(vo.url)
            } else {
                BreedImageModel("")
            }
        }

    override fun getSubBreedImage(
        masterBreed: String,
        subBreed: String
    ): Observable<BreedImageModel> =
        api.getSubBreedImage(masterBreed, subBreed).map { vo ->
            if (vo.status == "success") {
                BreedImageModel(vo.url)
            } else {
                BreedImageModel("")
            }
        }
}