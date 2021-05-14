package com.example.work_with_service.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.chayangkoon.champ.glide.ktx.load
import com.example.work_with_service.App
import com.example.work_with_service.R
import com.example.work_with_service.data.repository.PokemonRepository
import com.example.work_with_service.databinding.*
import com.example.work_with_service.presentation.adapters.PokemonAbilitiesAdapter
import com.example.work_with_service.presentation.adapters.PokemonTypesAdapter
import com.example.work_with_service.presentation.mappers.PokemonDetailMapper
import com.example.work_with_service.presentation.models.Pokemon
import com.example.work_with_service.presentation.viewmodels.PokemonDetailViewModel
import com.example.work_with_service.presentation.viewmodels.PokemonDetailViewModelFactory
import kotlinx.android.synthetic.main.item_pokemon_abilities.*
import kotlinx.android.synthetic.main.item_pokemon_types.*

class PokemonDetailPageFragment : Fragment() {
    private var binding: FragmentPokemonDetailBinding? = null
    private lateinit var viewModel: PokemonDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val app: App = (requireActivity().applicationContext as App)
        val repository: PokemonRepository = app.repository
        val mapper = PokemonDetailMapper()
        viewModel = ViewModelProvider(this, PokemonDetailViewModelFactory(mapper, repository))
            .get(PokemonDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_detail, container, false)
        binding?.lifecycleOwner = this
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigationToolBar()
        setupObserversPokemonDetail()
        setupObserversPokemon()
        setOnRetryConnectionClickListener()
        initPokemon()
    }

    private fun initNavigationToolBar() {
        binding?.let {
            it.collapsingToolbar.setupWithNavController(it.toolbar, findNavController())
        }
    }

    private fun initPokemon() {
        arguments?.let { bundle ->
            bundle.getSerializable(KEY_FOR_POKEMON_ARG)?.let {
                viewModel.fetchPokemonDetail(it as Pokemon)
            }
        }
    }

    private fun setupObserversPokemon() {
        viewModel.pokemon.observe(viewLifecycleOwner, { pokemon ->
            binding?.pokemon = pokemon
            binding?.ivPokemon?.load(pokemon.imageUrl) {
                placeholder(R.mipmap.ic_pokeball)
            }
        })
    }

    private fun setupObserversPokemonDetail() {
        viewModel.pokemonDetail.observe(viewLifecycleOwner, { pokemonDetail ->
            binding?.pokemonDetail = pokemonDetail.data
            pokemonDetail.data?.let {
                rvPokemonTypes.adapter = PokemonTypesAdapter(it.types)
                rvPokemonAbilities.adapter = PokemonAbilitiesAdapter(it.abilities)
            }
        })
    }

    private fun setOnRetryConnectionClickListener() {
        binding?.connectionError?.buttonRetryConnection?.setOnClickListener {
            initPokemon()
        }
    }

    companion object {
        private const val KEY_FOR_POKEMON_ARG = "namePokemon"
    }
}