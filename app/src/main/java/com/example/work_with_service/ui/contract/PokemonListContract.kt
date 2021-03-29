package com.example.work_with_service.ui.contract

import com.example.work_with_service.ui.model.PokemonAttributes

interface PokemonListContract {
    interface View {
        fun updatePokemonList(pokemonList: List<PokemonAttributes>)

        fun openDetailedPage(namePokemon: String)
    }

    interface Presenter {
        fun onViewCreated()

        fun onViewRestart()

        fun onItemPokemonClick(namePokemon: String)
    }
}