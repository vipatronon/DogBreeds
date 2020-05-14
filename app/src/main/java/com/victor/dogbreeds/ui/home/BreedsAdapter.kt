package com.victor.dogbreeds.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.victor.dogbreeds.R
import com.victor.dogbreeds.business.models.BreedsModel

class BreedsAdapter(
    private val breeds: List<BreedsModel>,
    private val action: ItemBreed.ItemBreedCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemBreed(inflater.inflate(R.layout.breed_name, parent, false))
    }

    override fun getItemCount(): Int = breeds.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemBreed) holder.bind(breeds[position], action)
    }
}