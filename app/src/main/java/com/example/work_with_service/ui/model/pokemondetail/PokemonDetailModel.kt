package com.example.work_with_service.ui.model.pokemondetail

import com.example.work_with_service.data.service.PokemonService
import com.example.work_with_service.data.model.PokemonDetail as DataPokemonDetail
import com.example.work_with_service.ui.model.Pokemon
import com.example.work_with_service.ui.mapper.PokemonDetailMapper

class PokemonDetailModel : PokemonDetailMapper() {
    private var onListReadyListener: ((PokemonDetail) -> Unit)? = null
    private var pokemonService: PokemonService = PokemonService()
    private var pokemon: Pokemon? = null
    private var pokemonDetail: PokemonDetail? = null

    fun fetchPokemonDetail(
        pokemon: Pokemon,
        onPokemonInfoReadyListener: (PokemonDetail) -> Unit,
        onServiceReturnError: () -> Unit
    ) {
        this.pokemon = pokemon
        onListReadyListener = onPokemonInfoReadyListener
        pokemonService.callPokemonDetail(
            pokemon.name,
            pokemon.abilityNames,
            pokemon.typeNames,
            this::onServiceFinishedWork,
            onServiceReturnError
        )
    }

    fun getPokemonDetail(pokemon: Pokemon): PokemonDetail? =
        when (pokemonDetail?.name.equals(pokemon.name, true)) {
            true -> pokemonDetail
            false -> null
        }

    private fun onServiceFinishedWork(pokemonAnswer: DataPokemonDetail) {
        pokemon?.let { pokemon ->
            map(pokemonAnswer, pokemon).let {
                pokemonDetail = it
                onListReadyListener?.invoke(it)
            }
        }
    }
}