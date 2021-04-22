package com.example.work_with_service.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.work_with_service.databinding.ListItemPokemonBinding
import com.example.work_with_service.ui.model.PokemonsAttributes.Attributes
import com.example.work_with_service.ui.utils.firstUpperCase
import com.example.work_with_service.ui.utils.setImageWithGlide

class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding: ListItemPokemonBinding = ListItemPokemonBinding.bind(view)

    fun bind(pokemonAttributes: Attributes, cityIconClickListener: (String) -> Unit) {
        setImageWithGlide(binding.root, pokemonAttributes.imageUrl, binding.ivPokemon)
        binding.tvNamePokemon.text = firstUpperCase(pokemonAttributes.name)
        binding.cardViewPokemon.setOnClickListener {
            cityIconClickListener(pokemonAttributes.name)
        }
    }
}