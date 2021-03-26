package com.example.work_with_service.ui.pokemondetail.contract

import com.example.work_with_service.ui.model.PokemonInfo

interface PokemonDetailsContract {
    interface View {
        fun showDetail(pokemonInfo: PokemonInfo)
    }

    interface Presenter {
//        fun onViewCreated()

        fun onViewGetPokemonName(namePokemon: String)
    }
}