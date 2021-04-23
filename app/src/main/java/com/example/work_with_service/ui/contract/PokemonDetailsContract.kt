package com.example.work_with_service.ui.contract

import com.example.work_with_service.ui.model.Pokemon
import com.example.work_with_service.ui.model.PokemonDetail

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