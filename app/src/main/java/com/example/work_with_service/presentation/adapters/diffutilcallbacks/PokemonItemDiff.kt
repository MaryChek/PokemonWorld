package com.example.work_with_service.presentation.adapters.diffutilcallbacks

import androidx.recyclerview.widget.DiffUtil
import com.example.work_with_service.presentation.models.Pokemon

class PokemonItemDiff : DiffUtil.ItemCallback<Pokemon>() {
    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
        oldItem.name == newItem.name

    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
        oldItem.name == newItem.name
}