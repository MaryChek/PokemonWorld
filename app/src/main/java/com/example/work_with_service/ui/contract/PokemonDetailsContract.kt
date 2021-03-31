package com.example.work_with_service.ui.contract

import com.example.work_with_service.ui.model.PokemonInfo

interface PokemonDetailsContract {
    interface View: BasePokemonContract.View {
        fun showDetail(pokemonInfo: PokemonInfo)
    }

    interface Presenter: BasePokemonContract.Presenter {
        fun onViewGetPokemonName(namePokemon: String)
    }
}