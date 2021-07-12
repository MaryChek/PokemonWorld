package com.example.work_with_service.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.chayangkoon.champ.glide.ktx.load
import com.example.work_with_service.R
import com.example.work_with_service.databinding.*
import com.example.work_with_service.presentation.adapters.PokemonAbilitiesAdapter
import com.example.work_with_service.presentation.adapters.PokemonTypesAdapter
import com.example.work_with_service.presentation.models.Pokemon
import com.example.work_with_service.presentation.models.PokemonDetail
import com.example.work_with_service.presentation.models.PokemonDetailModel
import com.example.work_with_service.presentation.navigation.FromPokemonDetail
import com.example.work_with_service.presentation.viewmodels.PokemonDetailViewModel

class PokemonDetailFragment : BasePokemonViewModelFragment<
        PokemonDetailModel, FromPokemonDetail, PokemonDetailViewModel>() {
    private var binding: FragmentPokemonDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigationToolBar()
        setOnRetryConnectionClickListener()
        initPokemon()
        initTitlesPokemonDetail()
    }

    private fun initNavigationToolBar() {
        binding?.let {
            it.collapsingToolbar.setupWithNavController(it.toolbar, findNavController())
        }
    }

    private fun initPokemon() {
        arguments?.let { bundle ->
            bundle.getSerializable(Pokemon::class.java.simpleName)?.let {
                viewModel.fetchPokemonDetail(it as Pokemon)
            }
        }
    }

    private fun initTitlesPokemonDetail() {
        binding?.cvPokemonMain?.tvBasExperience?.updateHeadText(R.string.base_experience)
        binding?.cvPokemonMain?.tvCaptureRate?.updateHeadText(R.string.capture_rate)
        binding?.cvPokemonMain?.tvHeight?.updateHeadText(R.string.pokemon_height)
        binding?.cvPokemonMain?.tvWeight?.updateHeadText(R.string.pokemon_weight)
        binding?.cvPokemonMain?.tvWeight?.updateHeadText(R.string.age_category)
        binding?.cvPokemonMain?.tvHabitat?.updateHeadText(R.string.habitat)
        binding?.cvPokemonMain?.tvColor?.updateHeadText(R.string.color)
    }

    private fun ItemPokemonMainBinding.updateHeadText(@StringRes titleResId: Int) {
        tvHeading.text = getString(titleResId)
    }

    override fun getViewModelClass(): Class<PokemonDetailViewModel> =
        PokemonDetailViewModel::class.java

    override fun goToScreen(destination: FromPokemonDetail): Any =
        when (destination) {
            is FromPokemonDetail.PreviousScreen -> navigateToPrevious()
        }

    override fun updateScreen(model: PokemonDetailModel) {
        binding?.ivPokemon?.load(model.imageUrl)
        binding?.toolbar?.title = model.name

        model.pokemonDetail?.let {
            updateAbilitiesAndTypesPokemon(model.pokemonDetail)
        }
        updateMainPokemonInfo(model)
        updateVisibility(model)
    }

    private fun updateAbilitiesAndTypesPokemon(pokemonDetail: PokemonDetail) {
        binding?.cvAbilities?.rvPokemonAbilities?.adapter =
            PokemonAbilitiesAdapter(resources, pokemonDetail.abilities)
        binding?.cvTypes?.rvPokemonTypes?.adapter =
            PokemonTypesAdapter(resources, pokemonDetail.types)
    }

    private fun updateMainPokemonInfo(model: PokemonDetailModel) {
        binding?.cvPokemonMain?.tvBasExperience?.updateValueText(model.baseExperience)
        binding?.cvPokemonMain?.tvCaptureRate?.updateValueText(
            getString(R.string.capture_rate_value, model.pokemonDetail?.captureRate)
        )
        binding?.cvPokemonMain?.tvHeight?.updateValueText(
            getString(R.string.pokemon_height_value, model.pokemonHeight)
        )
        binding?.cvPokemonMain?.tvWeight?.updateValueText(
            getString(R.string.pokemon_weight_value, model.pokemonWeight)
        )
        binding?.cvPokemonMain?.tvAgeCategory?.updateValueText(model.ageCategoryResId)
        binding?.cvPokemonMain?.tvHabitat?.updateValueText(model.pokemonDetail?.habitat)
        binding?.cvPokemonMain?.tvColor?.updateValueText(model.pokemonDetail?.color)
    }

    private fun ItemPokemonMainBinding.updateValueText(value: String?) {
        tvValue.text = value
    }

    private fun ItemPokemonMainBinding.updateValueText(valueTextResId: Int?) {
        valueTextResId?.let {
            tvValue.text = getString(valueTextResId)
        }
    }

    private fun updateVisibility(model: PokemonDetailModel) {
        binding?.collapsingToolbar?.updateVisibility(model.isToolbarVisible)
        binding?.cvPokemonMain?.root?.updateVisibility(model.isPokemonMainVisible)
        binding?.cvTypes?.root?.updateVisibility(model.isPokemonTypeVisible)
        binding?.cvAbilities?.root?.updateVisibility(model.isPokemonAbilitiesVisible)
        binding?.progressIndicator?.updateVisibility(model.isLoadingIndicatorVisible)
        binding?.connectionError?.root?.updateVisibility(model.isConnectionErrorViewVisible)
    }

    private fun setOnRetryConnectionClickListener() {
        binding?.connectionError?.buttonRetryConnection?.setOnClickListener {
            initPokemon()
        }
    }
}