package com.example.work_with_service.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.work_with_service.R
import com.example.work_with_service.databinding.ItemTypeBinding
import com.example.work_with_service.presentation.adapters.PokemonTypesAdapter.TypeViewHolder
import com.example.work_with_service.presentation.models.PokemonDetail

class PokemonTypesAdapter(private val pokemonTypes: List<PokemonDetail.Type>) :
    RecyclerView.Adapter<TypeViewHolder>() {

    override fun getItemCount(): Int =
        pokemonTypes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder =
        TypeViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_type, parent, (false)
            )
        )

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) =
        bindViewHolder(holder, pokemonTypes[position], position)

    private fun bindViewHolder(
        holder: TypeViewHolder,
        itemType: PokemonDetail.Type,
        position: Int
    ) =
        holder.binding.let {
            it.type = itemType
            it.position = position
        }
//            binding.apply {
//                tvNameType.text =
//                    root.resources.getString(
//                        R.string.name_with_number,
//                        (position + 1),
//                        itemType.name
//                    )
//                fillDamageItem(noDamageTo, itemType.noDamageTo)
//                fillDamageItem(doubleDamageTo, itemType.doubleDamageTo)
//                fillDamageItem(noDamageFrom, itemType.noDamageFrom)
//                fillDamageItem(doubleDamageFrom, itemType.doubleDamageFrom)
//            }

//    private fun fillDamageItem(itemDamage: LinearLayout, typeNames: List<String>) =
//        itemDamage.let {
//            when (typeNames.isNotEmpty()) {
//                true -> it.tvTypeNames.text = typeNames.joinToString(separator = ", ")
//                false -> it.visibility = View.GONE
//            }
//        }

    class TypeViewHolder(val binding: ItemTypeBinding) : RecyclerView.ViewHolder(binding.root)
}