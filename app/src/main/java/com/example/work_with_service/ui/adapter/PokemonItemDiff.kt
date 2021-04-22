package com.example.work_with_service.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.work_with_service.ui.model.PokemonsAttributes.Attributes

class PokemonItemDiff : DiffUtil.ItemCallback<Attributes>() {
    override fun areContentsTheSame(
        oldItem: Attributes,
        newItem: Attributes
    ): Boolean =
        oldItem.name == newItem.name

    override fun areItemsTheSame(oldItem: Attributes, newItem: Attributes): Boolean =
        oldItem.name == newItem.name
}