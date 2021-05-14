package com.example.work_with_service.presentation.models

import com.example.work_with_service.data.repository.PokemonRepository
import com.example.work_with_service.presentation.mappers.PokemonDetailMapper

class PokemonDetailModel(
    private val mapper: PokemonDetailMapper,
    private var pokemonService: PokemonRepository
){
//    private var onListReadyListener: ((PokemonDetail) -> Unit)? = null
//    private var pokemon: Pokemon? = null
//    private var pokemonDetail: PokemonDetail? = null
//
//    fun fetchPokemonDetail(
//        pokemon: Pokemon,
//        onPokemonInfoReadyListener: (PokemonDetail) -> Unit,
//        onServiceReturnError: () -> Unit
//    ) {
//        this.pokemon = pokemon
//        onListReadyListener = onPokemonInfoReadyListener
//        mapper.map(pokemon).let {
//            pokemonService.callPokemonDetail(
//                it.name,
//                it.abilityNames,
//                it.typeNames,
//                this::onServiceFinishedWork,
//                onServiceReturnError
//            )
//        }
//    }
//
//    fun getPokemonDetail(pokemon: Pokemon): PokemonDetail? =
//        when (pokemonDetail?.name.equals(pokemon.name, true)) {
//            true -> pokemonDetail
//            false -> null
//        }
//
//    private fun onServiceFinishedWork(pokemonAnswer: DomainPokemonDetail) {
//        pokemonDetail = mapper.map(pokemonAnswer)
//        pokemonDetail?.let {
//            onListReadyListener?.invoke(it)
//        }
//    }
}