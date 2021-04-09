package com.example.work_with_service.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.work_with_service.ui.model.pokemons.PokemonsAttributes.Attribute

class PokemonItemDiff : DiffUtil.ItemCallback<Attribute>() {
    override fun areContentsTheSame(
        oldItem: Attribute,
        newItem: Attribute
    ): Boolean =
        oldItem.name == newItem.name

    override fun areItemsTheSame(oldItem: Attribute, newItem: Attribute): Boolean =
        oldItem.name == newItem.name
}