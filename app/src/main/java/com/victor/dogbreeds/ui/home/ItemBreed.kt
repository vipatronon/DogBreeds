package com.victor.dogbreeds.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.victor.dogbreeds.business.models.BreedsModel
import kotlinx.android.synthetic.main.breed_name.view.*

class ItemBreed(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(breedsModel: BreedsModel) {
        itemView.apply {
            textLabel.text = breedsModel.breedName
        }
    }
}