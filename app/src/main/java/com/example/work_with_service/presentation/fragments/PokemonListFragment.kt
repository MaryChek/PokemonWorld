package com.example.work_with_service.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.work_with_service.R
import com.example.work_with_service.databinding.FragmentPokemonListBinding
import com.example.work_with_service.presentation.adapters.PokemonListAdapter
import com.example.work_with_service.presentation.models.PokemonListModel
import com.example.work_with_service.presentation.navigation.FromPokemonList
import com.example.work_with_service.presentation.viewmodels.PokemonListViewModel
import kotlinx.android.synthetic.main.fragment_pokemon_list.*

class PokemonListFragment : BasePokemonViewModelFragment<
        PokemonListModel, FromPokemonList, PokemonListViewModel>() {

    private var binding: FragmentPokemonListBinding? = null
    private var adapter: PokemonListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigationToolbar()
        initList()
        setOnRetryConnectionClickListener()
        viewModel.fetchPokemonList()
    }

    private fun initNavigationToolbar() {
        binding?.toolbar?.setupWithNavController(findNavController())
    }

    private fun initList() {
        adapter = PokemonListAdapter(viewModel::onItemPokemonClick)
        binding?.rvPokemon?.adapter = adapter
        addDividerItem()
    }

    private fun addDividerItem() {
        val dividerItem = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(
            resources, R.drawable.divider_pokemons, context?.theme
        )?.let { drawable ->
            dividerItem.setDrawable(drawable)
            rvPokemon?.addItemDecoration(dividerItem)
        }
    }

    override fun getViewModelClass(): Class<PokemonListViewModel> =
        PokemonListViewModel::class.java

    override fun goToScreen(destination: FromPokemonList): Any =
        when (destination) {
            is FromPokemonList.PokemonDetail ->
                navigate(destination.destinationResId, destination.pokemon)
            is FromPokemonList.PreviousScreen -> navigateToPrevious()
        }

    override fun updateScreen(model: PokemonListModel) {
        adapter?.submitList(model.pokemons)
        updateViewVisibility(model)
    }

    override fun navigateToPrevious() {
        activity?.finish()
    }

    private fun updateViewVisibility(model: PokemonListModel) {
        binding?.progressIndicator?.updateVisibility(model.isLoadingIndicatorVisible)
        binding?.connectionError?.root?.updateVisibility(model.isConnectionErrorViewVisible)
    }

    private fun setOnRetryConnectionClickListener() {
        binding?.connectionError?.buttonRetryConnection?.setOnClickListener {
            viewModel.onButtonRetryConnectionClick()
        }
    }
}