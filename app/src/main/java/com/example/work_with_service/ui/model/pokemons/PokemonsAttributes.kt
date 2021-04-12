package com.example.work_with_service.ui.model.pokemons

class PokemonsAttributes(
    val listAttributes: List<Attributes>
) {

    class Attributes(
        val imageUrl: String,
        val name: String
    )
}