package com.example.work_with_service.ui.contract

import com.example.work_with_service.data.entities.Pokemon
import com.example.work_with_service.ui.model.pokiattributes.ListPokemonAttributes.Attributes

interface PokemonListContract {
    interface View: BasePokemonContract.View {
        fun updatePokemonList(pokemonsAttributes: List<Attributes>)

//        fun openDetailedPage(namePokemon: String)
        fun openDetailedPage(pokemon: Pokemon)
    }

    interface Presenter: BasePokemonContract.Presenter {
        fun onViewCreated()

        fun onItemPokemonClick(namePokemon: String)
    }
}