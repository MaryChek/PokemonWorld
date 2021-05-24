package com.example.work_with_service.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.work_with_service.R
import com.example.work_with_service.databinding.ItemPokemonAbilitiesBinding
import com.example.work_with_service.databinding.ListItemPokemonBinding
import com.example.work_with_service.presentation.adapters.diffutilcallbacks.PokemonItemDiff
import com.example.work_with_service.presentation.adapters.viewholders.PokemonViewHolder
import com.example.work_with_service.presentation.models.Pokemon

class PokemonListAdapter(
    private val cityIconClickListener: (Pokemon) -> Unit
) : ListAdapter<Pokemon, PokemonViewHolder>(PokemonItemDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder =
        PokemonViewHolder(
            ListItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position), cityIconClickListener)
    }
}