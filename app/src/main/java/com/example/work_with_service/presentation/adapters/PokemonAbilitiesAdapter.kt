package com.example.work_with_service.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.work_with_service.R
import com.example.work_with_service.databinding.ItemAbilityBinding
import com.example.work_with_service.presentation.models.PokemonDetail

class PokemonAbilitiesAdapter(private val pokemonAbilities: List<PokemonDetail.Ability>) :
    RecyclerView.Adapter<PokemonAbilitiesAdapter.AbilityViewHolder>() {

    override fun getItemCount(): Int =
        pokemonAbilities.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilityViewHolder =
        AbilityViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_ability, parent, (false)
            )
        )


    override fun onBindViewHolder(holder: AbilityViewHolder, position: Int) =
        bindViewHolder(holder, pokemonAbilities[position], position)

    @SuppressLint("SetTextI18n")
    private fun bindViewHolder(
        holder: AbilityViewHolder,
        itemAbility: PokemonDetail.Ability,
        position: Int
    ) =
        holder.binding.let {
            it.ability = itemAbility
            it.position = position
        }

    class AbilityViewHolder(val binding: ItemAbilityBinding) : RecyclerView.ViewHolder(binding.root)
//
//    companion object {
//        private const val PARAGRAPH = "\t\t\t"
//    }
}