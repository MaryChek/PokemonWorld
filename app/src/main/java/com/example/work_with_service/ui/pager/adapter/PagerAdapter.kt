package com.example.work_with_service.ui.pager.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.work_with_service.ui.fragment.PokemonDetailPageFragment
import com.example.work_with_service.ui.fragment.PokemonListPageFragment

class PagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    var namePokemonForDetailPage = ""
        private set

    override fun getItemCount(): Int =
        COUNT_OF_PAGE

    override fun createFragment(position: Int): Fragment =
        when (position) {
            POSITION_OF_LIST_POKEMON -> PokemonListPageFragment()
            else -> PokemonDetailPageFragment().apply {
                arguments = bundleOf(KEY_FOR_NAME_POKEMON_ARG to namePokemonForDetailPage)
            }
        }

    fun setNamePokemonForDetailPage(newName: String) {
        namePokemonForDetailPage = newName
        notifyItemChanged(POSITION_OF_DETAIL_PAGE)
    }

    companion object {
        private const val KEY_FOR_NAME_POKEMON_ARG = "namePokemon"
        private const val COUNT_OF_PAGE = 2
        private const val POSITION_OF_LIST_POKEMON = 0
        private const val POSITION_OF_DETAIL_PAGE = 1
    }
}