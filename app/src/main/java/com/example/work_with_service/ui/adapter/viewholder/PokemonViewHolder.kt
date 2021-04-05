package com.example.work_with_service.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.work_with_service.databinding.ListItemPokemonBinding
import com.example.work_with_service.ui.model.pokiattributes.ListPokemonAttributes
import com.example.work_with_service.ui.utils.setImageWithGlide

class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding: ListItemPokemonBinding = ListItemPokemonBinding.bind(view)

    fun bind(
        pokemonAttributes: ListPokemonAttributes.Attributes,
        cityIconClickListener: (String) -> Unit
    ) {
        setImageWithGlide(binding.root, pokemonAttributes.imageUrl, binding.ivPokemon)
        binding.tvNamePokemon.text = pokemonAttributes.name
        binding.cardViewPokemon.setOnClickListener {
            cityIconClickListener(pokemonAttributes.name)
        }
    }
}