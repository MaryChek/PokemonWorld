package com.example.work_with_service.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.work_with_service.view.PokemonDetailPage
import com.example.work_with_service.view.PokemonListFragment

class PagerAdapter(
    fragment: Fragment,
    private var namePokemonForDetailPage: String? = null
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int =
        COUNT_OF_PAGE

    override fun createFragment(position: Int): Fragment =
        when (position) {
            POSITION_OF_LIST_POKEMON -> PokemonListFragment()
            else -> PokemonDetailPage().apply {
                arguments = bundleOf("namePokemon" to namePokemonForDetailPage)
            }
        }

    fun setNamePokemonForDetailPage(newName: String) {
        namePokemonForDetailPage = newName
        notifyItemChanged(POSITION_OF_DETAIL_PAGE)
    }

    companion object {
        private const val COUNT_OF_PAGE = 2
        private const val POSITION_OF_LIST_POKEMON = 0
        private const val POSITION_OF_DETAIL_PAGE = 1
    }
}