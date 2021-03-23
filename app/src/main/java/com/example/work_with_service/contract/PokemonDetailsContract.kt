package com.example.work_with_service.contract

import com.example.work_with_service.model.PokemonInfo

interface PokemonDetailsContract {
    interface View {
        fun showDetail(pokemonInfo: PokemonInfo)
    }

    interface Presenter {
//        fun onViewCreated()

        fun onViewGetPokemonName(namePokemon: String)
    }
}