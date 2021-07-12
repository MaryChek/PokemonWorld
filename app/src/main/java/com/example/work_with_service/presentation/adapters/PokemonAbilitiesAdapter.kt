package com.example.work_with_service.presentation.adapters

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.work_with_service.R
import com.example.work_with_service.databinding.ItemAbilityBinding
import com.example.work_with_service.presentation.models.PokemonDetail

class PokemonAbilitiesAdapter(
    private val resources: Resources,
    private val pokemonAbilities: List<PokemonDetail.Ability>
    ) :
    RecyclerView.Adapter<PokemonAbilitiesAdapter.AbilityViewHolder>() {

    override fun getItemCount(): Int =
        pokemonAbilities.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilityViewHolder =
        AbilityViewHolder(
            ItemAbilityBinding.inflate(
                LayoutInflater.from(parent.context), parent, (false)
            )
        )


    override fun onBindViewHolder(holder: AbilityViewHolder, position: Int) =
        bindViewHolder(holder, pokemonAbilities[position], position)

    private fun bindViewHolder(
        holder: AbilityViewHolder,
        itemAbility: PokemonDetail.Ability,
        position: Int
    ) =
        with(holder.binding) {
            val ability: String =
                resources.getString(R.string.name_with_number, position, itemAbility.name)
            tvAbilityName.text = ability
            tvAbilityDescription.text = itemAbility.effect
        }

    class AbilityViewHolder(val binding: ItemAbilityBinding) : RecyclerView.ViewHolder(binding.root)
}