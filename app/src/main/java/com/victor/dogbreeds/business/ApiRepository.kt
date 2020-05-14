package com.victor.dogbreeds.business

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
                    breeds.add(BreedsModel(breed.breedName))
                } else {
                    breed.subBreeds.forEach { subBreed ->
                        breeds.add(BreedsModel("$subBreed ${breed.breedName}"))
                    }
                }
            }
        }

        breeds
    }
}