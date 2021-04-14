package com.example.work_with_service.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.work_with_service.R
import com.example.work_with_service.databinding.ItemTypeBinding
import com.example.work_with_service.ui.adapter.PokemonTypesAdapter.TypeViewHolder
import com.example.work_with_service.ui.model.pokemondetail.PokemonDetail
import kotlinx.android.synthetic.main.item_type.view.*
import kotlinx.android.synthetic.main.tv_type_names.view.*

class PokemonTypesAdapter(private val pokemonTypes: List<PokemonDetail.Type>) :
    RecyclerView.Adapter<TypeViewHolder>() {

    override fun getItemCount(): Int =
        pokemonTypes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder =
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_type, parent, false
        ).run {
            TypeViewHolder(this)
        }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        bindViewHolder(holder, pokemonTypes[position], position)
    }

    private fun bindViewHolder(
        holder: TypeViewHolder,
        itemType: PokemonDetail.Type,
        position: Int
    ) {
        with(holder) {
            binding.apply {
                tvNameType.text =
                    root.resources.getString(R.string.name_type, (position + 1), itemType.name)
                fillDamageItem(noDamageTo, itemType.noDamageTo)
                fillDamageItem(doubleDamageTo, itemType.doubleDamageTo)
                fillDamageItem(noDamageFrom, itemType.noDamageFrom)
                fillDamageItem(doubleDamageFrom, itemType.doubleDamageFrom)
            }
        }
    }

    private fun fillDamageItem(itemDamage: LinearLayout, typeNames: List<String>) =
        itemDamage.apply {
            when (typeNames.isNotEmpty()) {
                true -> tvTypeNames.text = typeNames.joinToString(separator = ", ")
                false -> visibility = View.GONE
            }
        }

    class TypeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ItemTypeBinding = ItemTypeBinding.bind(view)
    }
}