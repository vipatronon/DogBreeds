package com.victor.dogbreeds.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.victor.dogbreeds.business.models.BreedsModel
import kotlinx.android.synthetic.main.breed_name.view.*

class ItemBreed(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(breedsModel: BreedsModel, action: ItemBreedCallback) {
        itemView.apply {
            if (breedsModel.subBreed.isEmpty()){
                textLabel.text = breedsModel.masterBreed
            } else {
                textLabel.text = "${breedsModel.subBreed} ${breedsModel.masterBreed}"
            }

            setOnClickListener {
                action.openBreedDetails(breedsModel)
            }
        }
    }

    interface ItemBreedCallback {
        fun openBreedDetails(breed: BreedsModel)
    }
}