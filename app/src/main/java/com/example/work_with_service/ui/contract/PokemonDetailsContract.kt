package com.example.work_with_service.ui.contract

import com.example.work_with_service.ui.model.PokemonAttributes
import com.example.work_with_service.ui.model.PokemonDetail

interface PokemonDetailsContract {
    interface View {
        fun showDetail(pokemonDetail: PokemonDetail)
    }

    interface Presenter {
        fun onViewGetPokemonAttributes(pokemonAttributes: PokemonAttributes)
    }
}