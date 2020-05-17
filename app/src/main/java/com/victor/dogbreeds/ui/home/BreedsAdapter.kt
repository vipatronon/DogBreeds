package com.victor.dogbreeds.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.victor.dogbreeds.R
import com.victor.dogbreeds.business.models.BreedsModel
import java.util.logging.Filter

class BreedsAdapter(
    private val breeds: List<BreedsModel>,
    private val itemBreedCallback: ItemBreed.ItemBreedCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private var filteredBreeds: List<BreedsModel>

    init {
        filteredBreeds = breeds
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemBreed(inflater.inflate(R.layout.breed_name, parent, false))
    }

    override fun getItemCount(): Int = filteredBreeds.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemBreed) holder.bind(filteredBreeds[position], itemBreedCallback)
    }

    override fun getFilter(): android.widget.Filter {
        return object : android.widget.Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charSearch = p0.toString()

                filteredBreeds = if(charSearch.isEmpty()){
                    breeds
                } else {
                    val resultList = mutableListOf<BreedsModel>()
                    for(row in breeds){
                        if (row.displayName.toLowerCase().contains(charSearch.toLowerCase())){
                            resultList.add(row)
                        }
                    }
                    resultList
                }

                val filterResults = FilterResults()
                filterResults.values = filteredBreeds
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredBreeds = p1?.values as List<BreedsModel>
                notifyDataSetChanged()
            }
        }
    }
}