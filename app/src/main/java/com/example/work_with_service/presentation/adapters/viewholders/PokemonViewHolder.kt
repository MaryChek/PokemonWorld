package com.example.work_with_service.presentation.adapters.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.work_with_service.databinding.ListItemPokemonBinding
import com.example.work_with_service.presentation.models.Pokemon

class PokemonViewHolder(private val binding: ListItemPokemonBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(pokemon: Pokemon, cityIconClickListener: (Pokemon) -> Unit) {
//        binding.pokemon = pokemon
        binding.cardViewPokemon.setOnClickListener {
            cityIconClickListener(pokemon)
        }
    }
}