package com.example.work_with_service.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.work_with_service.R
import com.example.work_with_service.databinding.ListItemPokemonBinding
import com.example.work_with_service.model.PokemonAttributes
import com.example.work_with_service.view.firstUpperCase

class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding: ListItemPokemonBinding = ListItemPokemonBinding.bind(view)

    fun bind(pokemonAttributes: PokemonAttributes, cityIconClickListener: (String) -> Unit) {
        Glide.with(binding.root)
            .asBitmap()
            .load(pokemonAttributes.imageUrl)
            .transform(CircleCrop())
            .placeholder(R.mipmap.ic_launcher_foreground)
            .into(binding.ivPokemon)
        binding.tvNamePokemon.text = firstUpperCase(pokemonAttributes.name)
        binding.cardViewPokemon.setOnClickListener {
            cityIconClickListener(pokemonAttributes.name)
        }
    }
}