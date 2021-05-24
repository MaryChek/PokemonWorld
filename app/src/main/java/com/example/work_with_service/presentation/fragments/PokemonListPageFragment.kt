package com.example.work_with_service.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.work_with_service.App
import com.example.work_with_service.R
import com.example.work_with_service.data.repository.PokemonRepository
import com.example.work_with_service.databinding.FragmentPokemonListBinding
import com.example.work_with_service.presentation.adapters.PokemonListAdapter
import com.example.work_with_service.presentation.mappers.PokemonListMapper
import com.example.work_with_service.presentation.models.Receipt
import com.example.work_with_service.presentation.viewmodels.PokemonListViewModel
import com.example.work_with_service.presentation.viewmodels.PokemonListViewModelFactory
import kotlinx.android.synthetic.main.fragment_pokemon_list.*

class PokemonListPageFragment : Fragment() {
    private var binding: FragmentPokemonListBinding? = null
    private lateinit var viewModel: PokemonListViewModel
    private var adapter: PokemonListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val app: App = (requireActivity().applicationContext as App)
        val repository: PokemonRepository = app.repository
        val mapper = PokemonListMapper()
        viewModel = ViewModelProvider((this), PokemonListViewModelFactory(mapper, repository))
            .get(PokemonListViewModel::class.java)
    }

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
        initNavigationToolBar()
        initList()
        setupObservers()
        setOnRetryConnectionClickListener()
        viewModel.fetchPokemonList()
    }

    private fun initNavigationToolBar() {
        binding?.toolbar?.setupWithNavController(findNavController())
    }

    private fun initList() {
        adapter = PokemonListAdapter(viewModel::onItemPokemonClick)
        binding?.rvPokemon?.adapter = adapter
        addDividerItem()
    }

    private fun addDividerItem() {
        val dividerItem = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.divider_pokemons, context?.theme)?.let {
            dividerItem.setDrawable(it)
            rvPokemon?.addItemDecoration(dividerItem)
        }
    }

    private fun setupObservers() {
        viewModel.receipt.observe(viewLifecycleOwner, { receipt ->
            when (receipt.status) {
                Receipt.Status.LOADING -> showLoadingIndicator()
                Receipt.Status.SUCCESS -> {
                    hideConnectionErrorMessage()
                    hideLoadingIndicator()
                }
                Receipt.Status.ERROR -> showConnectionErrorMessage()
            }
        })
        viewModel.pokemonList.observe(viewLifecycleOwner, { pokemons ->
            adapter?.submitList(pokemons)
        })
        viewModel.navigation.observe(viewLifecycleOwner, { navigation ->
            findNavController().navigate(navigation.navigateToId, navigation.arguments)
        })
    }

    private fun showLoadingIndicator() {
        binding?.progressIndicator?.visibility = VISIBLE
    }

    private fun hideLoadingIndicator() {
        binding?.progressIndicator?.visibility = GONE
    }

    private fun showConnectionErrorMessage() {
        binding?.connectionError?.root?.visibility = VISIBLE
        setOnRetryConnectionClickListener()
    }

    private fun setOnRetryConnectionClickListener() {
        binding?.connectionError?.buttonRetryConnection?.setOnClickListener {
            viewModel.onButtonRetryConnectionClick()
        }
    }

    private fun hideConnectionErrorMessage() {
        binding?.connectionError?.root?.visibility = GONE
    }
}