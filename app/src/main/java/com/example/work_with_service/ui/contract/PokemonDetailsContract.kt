package com.example.work_with_service.ui.contract

import com.example.work_with_service.ui.model.PokemonInfo

interface PokemonDetailsContract {
    interface View {
        fun showDetail(pokemonInfo: PokemonInfo)
    }

    interface Presenter {
        fun onViewGetPokemonName(namePokemon: String)
    }
}