package com.example.work_with_service.ui.model

import com.example.work_with_service.data.service.PokemonService
import com.example.work_with_service.data.model.Pokemon
import com.example.work_with_service.ui.mapper.PokemonDetailMapper
import com.example.work_with_service.ui.model.pokiattributes.PokemonDetail
import com.example.work_with_service.ui.model.pokiattributes.PokiAttributes
import com.example.work_with_service.ui.model.servicepokemonanswer.PokiDetail

class PokemonDetailModel : PokemonDetailMapper() {
    private var onListReadyListener: ((PokiAttributes) -> Unit)? = null
    private var pokemonService: PokemonService = PokemonService()
    private var pokemon: Pokemon? = null
    private var pokemonDetail: PokemonDetail? = null

    fun fetchPokemonDetail(
        pokemon: Pokemon,
        onPokemonInfoReadyListener: (PokiAttributes) -> Unit,
        onServiceReturnError: () -> Unit
    ) {
        this.pokemon = pokemon
        onListReadyListener = onPokemonInfoReadyListener
        pokemonService.callPokemonDetail(pokemon, this::onServiceFinishedWork, onServiceReturnError)
    }

    fun getPokemonDetail(pokemon: Pokemon): PokemonDetail? =
        when (pokemonDetail?.name == pokemon.name) {
            true -> pokemonDetail
            false -> null
        }

    private fun onServiceFinishedWork(pokemonAnswer: PokiDetail) {
        map(pokemonAnswer).let {
            pokemonDetail = it
            onListReadyListener?.invoke(it)
        }
    }
}