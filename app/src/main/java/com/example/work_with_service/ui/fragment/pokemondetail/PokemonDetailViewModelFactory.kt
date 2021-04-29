package com.example.work_with_service.ui.fragment.pokemondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.work_with_service.data.repository.PokemonRepository
import com.example.work_with_service.ui.mapper.PokemonListMapper
import java.lang.IllegalArgumentException

class PokemonDetailViewModelFactory(
    private val mapper: PokemonListMapper,
    private var pokemonService: PokemonRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass == PokemonDetailViewModel::class.java) {
            true -> PokemonDetailViewModel(mapper, pokemonService) as T
            else -> throw IllegalArgumentException("Factory cannot make ViewModel of type ${modelClass.simpleName}")
        }
}