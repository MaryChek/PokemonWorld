package com.example.work_with_service.ui.fragment.pager.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.work_with_service.data.entities.Pokemon
import com.example.work_with_service.ui.fragment.PokemonDetailPageFragment
import com.example.work_with_service.ui.fragment.PokemonListPageFragment

class PagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    var pokemonForDetailPage: Pokemon? = null
        private set

    override fun getItemCount(): Int =
        COUNT_OF_PAGE

    override fun createFragment(position: Int): Fragment =
        when (position) {
            POSITION_OF_LIST_POKEMON -> PokemonListPageFragment()
            else -> PokemonDetailPageFragment().apply {
                pokemonForDetailPage?.let {
                    arguments = bundleOf(KEY_FOR_NAME_POKEMON_ARG to it)
                }
            }
        }

    fun setNamePokemonForDetailPage(newPokemon: Pokemon) {
        pokemonForDetailPage = newPokemon
        notifyItemChanged(POSITION_OF_DETAIL_PAGE)
    }

    companion object {
        private const val KEY_FOR_NAME_POKEMON_ARG = "namePokemon"
        private const val COUNT_OF_PAGE = 2
        private const val POSITION_OF_LIST_POKEMON = 0
        private const val POSITION_OF_DETAIL_PAGE = 1
    }
}