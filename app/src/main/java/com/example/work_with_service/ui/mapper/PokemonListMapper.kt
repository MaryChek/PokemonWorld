package com.example.work_with_service.ui.mapper

import com.example.work_with_service.data.entities.Pokemon
import com.example.work_with_service.ui.model.pokiattributes.ListPokemonAttributes.Attributes

open class PokemonListMapper : BasePokemonMapper() {
    fun map(listPokemon: List<Pokemon>): List<Attributes> =
        listPokemon.map {
            Attributes(it.sprites.frontDefault, firstUpperCase(it.name))
        }
}