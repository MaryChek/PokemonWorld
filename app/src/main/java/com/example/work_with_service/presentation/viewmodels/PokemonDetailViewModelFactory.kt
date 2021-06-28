package com.example.work_with_service.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.work_with_service.domain.interactor.PokemonInteractor
import com.example.work_with_service.presentation.mappers.PokemonDetailMapper
import java.lang.IllegalArgumentException

class PokemonDetailViewModelFactory(
    private val mapper: PokemonDetailMapper,
    private var pokemonInteractor: PokemonInteractor
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass == PokemonDetailViewModel::class.java) {
            true -> PokemonDetailViewModel(mapper, pokemonInteractor) as T
            else -> throw IllegalArgumentException("Factory cannot make ViewModel of type ${modelClass.simpleName}")
        }
}