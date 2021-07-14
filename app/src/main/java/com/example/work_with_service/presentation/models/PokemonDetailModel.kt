package com.example.work_with_service.presentation.models

import androidx.annotation.StringRes
import com.example.work_with_service.R

class PokemonDetailModel(
    val pokemon: Pokemon? = null,
    val name: String? = null,
    val imageUrl: String? = null,
    val baseExperience: String? = null,
    val pokemonHeight: String? = null,
    val pokemonWeight: Double? = null,
    val pokemonDetail: PokemonDetail? = null,
    val resource: State = State.loading(),
) : RefreshableModel(resource) {

    val isToolbarVisible: Boolean = name != null
    val isPokemonMainVisible: Boolean = pokemonDetail != null
    val isPokemonTypeVisible: Boolean = pokemonDetail != null
    val isPokemonAbilitiesVisible: Boolean = pokemonDetail != null

    @StringRes
    val ageCategoryResId: Int =
        when (pokemonDetail?.isBaby == true) {
            true -> R.string.baby
            false -> R.string.adult
        }

//    val isLoadingIndicatorVisible: Boolean = state.isInLoadingState()
//    val isConnectionErrorViewVisible: Boolean = state.isInErrorState()
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