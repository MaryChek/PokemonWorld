package com.example.work_with_service.ui.contract

import com.example.work_with_service.ui.model.Pokemon
import com.example.work_with_service.ui.model.PokemonsAttributes.Attributes

interface PokemonListContract {
    interface View : BasePokemonContract.View {
        fun updatePokemonList(pokemonsAttributes: List<Attributes>)

        fun openDetailedPage(pokemon: Pokemon)
    }

    interface Presenter : BasePokemonContract.Presenter {
        fun onViewCreated()

        fun onItemPokemonClick(namePokemon: String)
    }
}