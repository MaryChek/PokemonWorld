package com.example.work_with_service.ui.contract

import com.example.work_with_service.ui.model.Pokemon

interface PokemonListContract {
    interface View : BasePokemonContract.View {
        fun updatePokemonList(pokemons: List<Pokemon>)

        fun openDetailedPage(pokemon: Pokemon)
    }

    interface Presenter : BasePokemonContract.Presenter {
        fun onViewCreated()

        fun onItemPokemonClick(namePokemon: String)
    }
}