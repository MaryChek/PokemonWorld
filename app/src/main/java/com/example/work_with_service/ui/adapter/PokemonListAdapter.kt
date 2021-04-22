package com.example.work_with_service.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.work_with_service.R
import com.example.work_with_service.ui.adapter.viewholder.PokemonViewHolder
import com.example.work_with_service.ui.model.PokemonsAttributes.Attributes

class PokemonListAdapter(
    private val cityIconClickListener: (String) -> Unit
) : ListAdapter<Attributes, PokemonViewHolder>(PokemonItemDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder =
        LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_pokemon, parent, false
        ).run {
            PokemonViewHolder(this)
        }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position), cityIconClickListener)
    }
}