package com.example.work_with_service.ui.fragment.pokemonlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.work_with_service.App
import com.example.work_with_service.R
import com.example.work_with_service.ui.contract.PokemonListContract
import com.example.work_with_service.databinding.PokemonListPageBinding
import com.example.work_with_service.ui.adapter.PokemonListAdapter
import com.example.work_with_service.ui.fragment.pokemondetail.DetailPage
import com.example.work_with_service.ui.model.PokemonAttributes
import com.example.work_with_service.ui.model.PokemonModel
import com.example.work_with_service.ui.presenter.PokemonListPresenter

class PokemonListPage : Fragment(), PokemonListContract.View {
    private var binding: PokemonListPageBinding? = null
    private var rvPokemon: RecyclerView? = null
    private var adapter: PokemonListAdapter? = null
    private lateinit var presenter: PokemonListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val model: PokemonModel = (requireActivity().applicationContext as App).pokemonModel
        presenter = PokemonListPresenter(model, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PokemonListPageBinding.inflate(inflater, container, false)
        rvPokemon = binding?.rvPokemon
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        when (savedInstanceState == null) {
            true -> presenter.onViewCreated()
            false -> presenter.onViewRestart()
        }
    }

    private fun initList() {
        adapter = PokemonListAdapter(this::onItemClick)
        rvPokemon?.adapter = adapter
        addDividerItem()
    }

    private fun onItemClick(namePokemon: String) =
        presenter.onItemPokemonClick(namePokemon)

    override fun openDetailedPage(namePokemon: String) =
        (requireParentFragment() as DetailPage).openDetailedPage(namePokemon)

    private fun addDividerItem() {
        val dividerItem = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.divider_pokemons, context?.theme)?.let {
            dividerItem.setDrawable(it)
            rvPokemon?.addItemDecoration(dividerItem)
        }
    }

    override fun updatePokemonList(pokemonList: List<PokemonAttributes>) {
        adapter?.submitList(pokemonList)
    }
}