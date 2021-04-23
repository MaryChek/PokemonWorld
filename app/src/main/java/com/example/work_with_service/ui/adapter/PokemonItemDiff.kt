package com.example.work_with_service.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.work_with_service.ui.model.Pokemon

class PokemonItemDiff : DiffUtil.ItemCallback<Pokemon>() {
    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
        oldItem.name == newItem.name

    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
        oldItem.name == newItem.name
}