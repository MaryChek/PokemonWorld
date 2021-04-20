package com.example.work_with_service.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chayangkoon.champ.glide.ktx.load
import com.example.work_with_service.R
import com.example.work_with_service.databinding.ListItemPokemonBinding
import com.example.work_with_service.ui.model.pokemons.PokemonsAttributes

class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding: ListItemPokemonBinding = ListItemPokemonBinding.bind(view)

    fun bind(
        pokemon: PokemonsAttributes.Attributes,
        cityIconClickListener: (String) -> Unit
    ) =
        binding.let {
            it.ivPokemon.load(pokemon.imageUrl) {
                placeholder(R.mipmap.ic_pokeball_foreground)
            }
            it.tvNamePokemon.text = pokemon.name
            it.cardViewPokemon.setOnClickListener {
                cityIconClickListener(pokemon.name)
            }
        }
}