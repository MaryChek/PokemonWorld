package com.example.work_with_service.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.work_with_service.databinding.ListItemPokemonBinding
import com.example.work_with_service.ui.model.Pokemon
import com.example.work_with_service.ui.utils.firstUpperCase
import com.example.work_with_service.ui.utils.setImageWithGlide

class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding: ListItemPokemonBinding = ListItemPokemonBinding.bind(view)

    fun bind(pokemons: Pokemon, cityIconClickListener: (String) -> Unit) {
        setImageWithGlide(binding.root, pokemons.imageUrl, binding.ivPokemon)
        binding.tvNamePokemon.text = firstUpperCase(pokemons.name)
        binding.cardViewPokemon.setOnClickListener {
            cityIconClickListener(pokemons.name)
        }
    }
}