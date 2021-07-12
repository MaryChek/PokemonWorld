package com.example.work_with_service.presentation.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.example.work_with_service.R
import com.example.work_with_service.databinding.ItemDamageBinding
import com.example.work_with_service.databinding.ItemTypeBinding
import com.example.work_with_service.presentation.adapters.PokemonTypesAdapter.TypeViewHolder
import com.example.work_with_service.presentation.models.PokemonDetail

class PokemonTypesAdapter(
    private val resources: Resources,
    private val pokemonTypes: List<PokemonDetail.Type>
) : RecyclerView.Adapter<TypeViewHolder>() {

    override fun getItemCount(): Int =
        pokemonTypes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder =
        TypeViewHolder(
            ItemTypeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) =
        bindViewHolder(holder, pokemonTypes[position], position)

    private fun bindViewHolder(
        holder: TypeViewHolder,
        itemType: PokemonDetail.Type,
        position: Int
    ) =
        with(holder.binding) {
            val type: String =
                resources.getString(R.string.name_with_number, position, itemType.name)
            tvNameType.text = type
            itemNoDamageTo.updateHeadAndValueTexts(R.string.no_damage_to, itemType.doubleDamageTo)
        }

    private fun ItemDamageBinding.updateHeadAndValueTexts(
        @StringRes headTextResId: Int,
        damage: List<String>
    ) {
        tvHeading.text = resources.getString(headTextResId)
        if (damage.isNotEmpty()) {
            root.visibility = View.VISIBLE
            val typesDamage: String = damage.joinToString(separator = ", ")
            tvValue.text = typesDamage
        } else {
            root.visibility = View.GONE
        }
    }

    class TypeViewHolder(val binding: ItemTypeBinding) : RecyclerView.ViewHolder(binding.root)
}