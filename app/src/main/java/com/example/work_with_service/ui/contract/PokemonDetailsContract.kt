package com.example.work_with_service.ui.contract

import com.example.work_with_service.data.model.Pokemon
import com.example.work_with_service.ui.model.pokiattributes.PokemonDetail

interface PokemonDetailsContract {
    interface View: BasePokemonContract.View {
        fun showDetail(pokemonDetail: PokemonDetail)
    }

    interface Presenter: BasePokemonContract.Presenter {
        fun onViewGetPokemonName(pokemon: Pokemon)
    }
}