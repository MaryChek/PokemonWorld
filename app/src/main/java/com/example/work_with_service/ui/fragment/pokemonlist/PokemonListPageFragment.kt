package com.example.work_with_service.ui.fragment.pokemonlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.View.GONE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.work_with_service.ui.App
import com.example.work_with_service.R
import com.example.work_with_service.ui.model.Pokemon
import com.example.work_with_service.databinding.FragmentPokemonListBinding
import com.example.work_with_service.ui.contract.PokemonListContract
import com.example.work_with_service.ui.adapter.PokemonListAdapter
import com.example.work_with_service.ui.model.PokemonListModel
import com.example.work_with_service.ui.model.PokemonsAttributes.Attributes
import com.example.work_with_service.ui.presenter.PokemonListPresenter
import com.example.work_with_service.ui.fragment.pokemondetail.DetailPage

class PokemonListPageFragment : Fragment(), PokemonListContract.View {
    private var binding: FragmentPokemonListBinding? = null
    private var rvPokemon: RecyclerView? = null
    private var adapter: PokemonListAdapter? = null
    private lateinit var presenter: PokemonListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val app: App = (requireActivity().applicationContext as App)
        val model: PokemonListModel = app.pokemonListModel
        presenter = PokemonListPresenter(model, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        rvPokemon = binding?.rvPokemon
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        setTitle()
        presenter.onViewCreated()
    }

    private fun initList() {
        adapter = PokemonListAdapter(presenter::onItemPokemonClick)
        rvPokemon?.adapter = adapter
        addDividerItem()
    }

    private fun setTitle() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
    }

    override fun showLoadingIndicator() {
        binding?.progressIndicator?.visibility = VISIBLE
    }

    override fun hideLoadingIndicator() {
        binding?.progressIndicator?.visibility = GONE
    }

    override fun showConnectionErrorMessage() {
        binding?.connectionError?.root?.visibility = VISIBLE
        setOnRetryConnectionClickListener()
    }

    private fun setOnRetryConnectionClickListener() {
        binding?.connectionError?.buttonRetryConnection?.setOnClickListener {
            presenter.onRetryConnectionClick()
        }
    }

    override fun hideConnectionErrorMessage() {
        binding?.connectionError?.root?.visibility = GONE
    }

    override fun openDetailedPage(pokemon: Pokemon) {
        when (val parent = requireParentFragment()) {
            is DetailPage -> parent.openDetailedPage(pokemon)
            else -> Log.w(null, PARENT_FRAGMENT_ERROR)
        }
    }

    private fun addDividerItem() {
        val dividerItem = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.divider_pokemons, context?.theme)?.let {
            dividerItem.setDrawable(it)
            rvPokemon?.addItemDecoration(dividerItem)
        }
    }

    override fun updatePokemonList(pokemonsAttributes: List<Attributes>) {
        adapter?.submitList(pokemonsAttributes)
    }

    companion object {
        private const val PARENT_FRAGMENT_ERROR =
            "Pager fragment parent does not inherit interface DetailPage"
    }
}