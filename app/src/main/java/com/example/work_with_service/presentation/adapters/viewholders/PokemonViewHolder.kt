package com.example.work_with_service.presentation.adapters.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.chayangkoon.champ.glide.ktx.load
import com.example.work_with_service.databinding.ListItemPokemonBinding
import com.example.work_with_service.presentation.models.Pokemon

class PokemonViewHolder(private val binding: ListItemPokemonBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(pokemon: Pokemon, pokemonClickListener: (Pokemon) -> Unit) {
        binding.cardViewPokemon.setOnClickListener {
            pokemonClickListener(pokemon)
        }
        binding.ivPokemon.load(pokemon.imageUrl)
        binding.tvNamePokemon.text = pokemon.name
    }
}