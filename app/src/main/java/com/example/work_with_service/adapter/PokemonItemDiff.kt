package com.example.work_with_service.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.work_with_service.model.PokemonAttributes

class PokemonItemDiff : DiffUtil.ItemCallback<PokemonAttributes>() {
    override fun areContentsTheSame(oldItem: PokemonAttributes, newItem: PokemonAttributes): Boolean =
        oldItem.name == newItem.name

    override fun areItemsTheSame(oldItem: PokemonAttributes, newItem: PokemonAttributes): Boolean =
        oldItem.name == newItem.name
}