package com.example.work_with_service.ui.pager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING
import com.example.work_with_service.ui.fragment.pokemondetail.DetailPage
import com.example.work_with_service.databinding.PokemonPagerFragmentBinding
import com.example.work_with_service.ui.pager.adapter.PagerAdapter

class PagerPokemonFragment : Fragment(),
    DetailPage {
    private var binding: PokemonPagerFragmentBinding? = null
    private var pokemonPager: ViewPager2? = null
    private var adapter: PagerAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callBack: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() =
                if (pokemonPager?.currentItem == 1) {
                    pokemonPager?.currentItem = 0
                } else {
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callBack)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PokemonPagerFragmentBinding.inflate(inflater, container, false)
        pokemonPager = binding?.pokemonPager
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PagerAdapter(this)
        pokemonPager?.adapter = adapter
        registerOnPageChange()
    }

    private fun registerOnPageChange() {
        pokemonPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                pokemonPager?.isUserInputEnabled =
                    !(state == SCROLL_STATE_DRAGGING && pokemonPager?.currentItem == 0)
            }
        })
    }

    override fun openDetailedPage(namePokemon: String) {
        adapter?.setNamePokemonForDetailPage(namePokemon)
        pokemonPager?.currentItem = DETAIL_PAGE_POSITION
    }

    companion object {
        private const val DETAIL_PAGE_POSITION = 1
    }
}