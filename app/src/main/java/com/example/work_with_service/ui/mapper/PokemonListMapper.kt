package com.example.work_with_service.ui.mapper

import com.example.work_with_service.data.model.Pokemon
import com.example.work_with_service.ui.model.pokiattributes.ListPokemonAttributes.Attributes

open class PokemonListMapper : BasePokemonMapper() {
    fun map(listPokemon: List<Pokemon>): List<Attributes> =
        listPokemon.map {
            Attributes(it.frontDefault, firstUpperCase(it.name))
        }
}