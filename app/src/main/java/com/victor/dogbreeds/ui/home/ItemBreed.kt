package com.victor.dogbreeds.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.victor.dogbreeds.R
import com.victor.dogbreeds.business.models.BreedsModel
import kotlinx.android.synthetic.main.breed_name.view.*

class ItemBreed(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(breedsModel: BreedsModel, action: ItemBreedCallback) {
        itemView.apply {
            if (breedsModel.subBreed.isEmpty()){
                breedNameTextLabel.text = breedsModel.masterBreed
            } else {
                breedNameTextLabel.text = "${breedsModel.subBreed} ${breedsModel.masterBreed}"
            }

            swapFavoriteSpriteResource(breedsModel.isFavorite)

            setOnClickListener {
                action.openBreedDetails(breedsModel)
            }

            breedNameFavoriteIcon.setOnClickListener {
                action.favoriteBreed(breedsModel)
                swapFavoriteSpriteResource(breedsModel.isFavorite)
            }
        }
    }

    private fun swapFavoriteSpriteResource (isFavorite: Boolean){
        itemView.apply {
            if (isFavorite){
                breedNameFavoriteIcon.setImageResource(R.drawable.ic_favorite_filled)
            } else{
                breedNameFavoriteIcon.setImageResource(R.drawable.ic_favorite)
            }
        }
    }

    interface ItemBreedCallback {
        fun openBreedDetails(breed: BreedsModel)
        fun favoriteBreed(breed: BreedsModel)
    }
}