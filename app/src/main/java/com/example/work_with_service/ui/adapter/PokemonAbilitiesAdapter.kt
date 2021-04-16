package com.example.work_with_service.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.work_with_service.R
import com.example.work_with_service.databinding.ItemAbilityBinding
import com.example.work_with_service.ui.model.pokemondetail.PokemonDetail

class PokemonAbilitiesAdapter(private val pokemonAbilities: List<PokemonDetail.Ability>) :
    RecyclerView.Adapter<PokemonAbilitiesAdapter.AbilityViewHolder>() {

    override fun getItemCount(): Int =
        pokemonAbilities.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilityViewHolder =
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_ability, parent, false
        ).run {
            AbilityViewHolder(this)
        }

    override fun onBindViewHolder(holder: AbilityViewHolder, position: Int) =
        bindViewHolder(holder, pokemonAbilities[position], position)

    @SuppressLint("SetTextI18n")
    private fun bindViewHolder(
        holder: AbilityViewHolder,
        itemAbility: PokemonDetail.Ability,
        position: Int
    ) {
        with(holder) {
            binding.apply {
                tvAbilityName.text =
                    root.resources.getString(
                        R.string.name_with_number,
                        (position + 1),
                        itemAbility.name
                    )
                tvAbilityDescription.text = PARAGRAPH + itemAbility.effect
            }
        }
    }

    class AbilityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ItemAbilityBinding = ItemAbilityBinding.bind(view)
    }

    companion object {
        private const val PARAGRAPH = "\t\t\t"
    }
}