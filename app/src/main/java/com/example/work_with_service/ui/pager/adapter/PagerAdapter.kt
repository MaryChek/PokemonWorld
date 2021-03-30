package com.example.work_with_service.ui.pager.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.work_with_service.data.entities.Pokemon
import com.example.work_with_service.ui.fragment.pokemondetail.PokemonDetailPage
import com.example.work_with_service.ui.fragment.pokemonlist.PokemonListPage
import com.example.work_with_service.ui.model.PokemonAttributes

class PagerAdapter(
    fragment: Fragment,
    private var pokemonAttributesForDetailPage: PokemonAttributes? = null
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int =
        COUNT_OF_PAGE

    override fun createFragment(position: Int): Fragment =
        when (position) {
            POSITION_OF_LIST_POKEMON -> PokemonListPage()
            else -> PokemonDetailPage()
                .apply {
                    arguments = bundleOf(
                        KEY_FOR_NAME_POKEMON_ARG to pokemonAttributesForDetailPage
                    )
                }
        }

    fun setNamePokemonForDetailPage(newPokemonAttributes: PokemonAttributes) {
        pokemonAttributesForDetailPage = newPokemonAttributes
        notifyItemChanged(POSITION_OF_DETAIL_PAGE)
    }

    companion object {
        const val KEY_FOR_NAME_POKEMON_ARG = "pokemonAttributes"
        private const val COUNT_OF_PAGE = 2
        private const val POSITION_OF_LIST_POKEMON = 0
        private const val POSITION_OF_DETAIL_PAGE = 1
    }
}