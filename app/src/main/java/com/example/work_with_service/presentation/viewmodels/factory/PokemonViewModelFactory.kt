package com.example.work_with_service.presentation.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.work_with_service.domain.interactor.PokemonInteractor
import com.example.work_with_service.presentation.mappers.PokemonDetailMapper
import com.example.work_with_service.presentation.mappers.PokemonListMapper
import com.example.work_with_service.presentation.viewmodels.PokemonDetailViewModel
import com.example.work_with_service.presentation.viewmodels.PokemonListViewModel
import java.lang.IllegalArgumentException

class PokemonViewModelFactory(
    private val interactor: PokemonInteractor,
    private val pokemonListMapper: PokemonListMapper,
    private val pokemonDetailMapper: PokemonDetailMapper,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            PokemonListViewModel::class.java ->
                PokemonListViewModel(pokemonListMapper, interactor) as T
            PokemonDetailViewModel::class.java ->
                PokemonDetailViewModel(pokemonDetailMapper, interactor) as T
            else -> throw IllegalArgumentException("Factory cannot make ViewModel of type ${modelClass.simpleName}")
        }
}