package com.example.work_with_service.ui.fragment.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING
import com.example.work_with_service.App
import com.example.work_with_service.ui.model.Pokemon
import com.example.work_with_service.ui.fragment.DetailPage
import com.example.work_with_service.databinding.FragmentPokemonPagerBinding
import com.example.work_with_service.ui.activity.MainActivity
import com.example.work_with_service.ui.contract.PagerPokemonContract
import com.example.work_with_service.ui.model.PagerTitlesModel
import com.example.work_with_service.ui.fragment.pager.adapter.PagerAdapter
import com.example.work_with_service.ui.presenter.PagerPokemonPresenter

class PagerPokemonFragment : Fragment(),
    DetailPage, PagerPokemonContract.View {
    private var binding: FragmentPokemonPagerBinding? = null
    private lateinit var presenter: PagerPokemonPresenter
    private val pokemonPager: ViewPager2?
        get() = binding?.pokemonPager
    private var adapter: PagerAdapter? = null
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val app: App = (requireActivity().applicationContext as App)
        val model: PagerTitlesModel = app.pagerTitlesModel
        presenter = PagerPokemonPresenter(model, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonPagerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PagerAdapter(this)
        pokemonPager?.adapter = adapter
        registerOnPageChange()
        initOnBackPressedListener()
    }

    private fun registerOnPageChange() {
        pokemonPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                presenter.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (state == SCROLL_STATE_DRAGGING) {
                    pokemonPager?.currentItem?.let {
                        presenter.onPageStartedScrolling(it)
                    }
                }
            }
        })
    }

    private fun initOnBackPressedListener() {
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                pokemonPager?.currentItem?.let { currentItem ->
                    presenter.onBackPressed(currentItem)
                }
            }
        }
        requireActivity().onBackPressedDispatcher
            .addCallback(this, onBackPressedCallback)
    }

    override fun disableOnBackPressedCallback() {
        onBackPressedCallback.isEnabled = false
    }

    override fun onBackPressed() =
        requireActivity().onBackPressed()

    override fun disableSwapPage() {
        pokemonPager?.isUserInputEnabled = false
    }

    override fun openDetailedPage(pokemon: Pokemon) {
        presenter.onDetailPageOpens(pokemon.name)
        adapter?.setNamePokemonForDetailPage(pokemon)
    }

    override fun selectItemOnPager(position: Int) {
        pokemonPager?.currentItem = position
    }

    override fun setTitleByPosition(position: Int, title: String) {
        (activity as MainActivity).supportActionBar?.title = title
    }
}