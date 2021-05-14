package com.example.work_with_service.presentation.contract

import com.example.work_with_service.presentation.models.Pokemon
import com.example.work_with_service.presentation.models.PokemonDetail

interface PokemonDetailsContract {
    interface View : BasePokemonContract.View {
        fun showPokemonMain(detail: PokemonDetail, pokemon: Pokemon)

        fun showPokemonAbilities(abilities: List<PokemonDetail.Ability>)

        fun showPokemonTypes(types: List<PokemonDetail.Type>)
    }

    interface Presenter : BasePokemonContract.Presenter {
        fun init(pokemon: Pokemon)
    }
}