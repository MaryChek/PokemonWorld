package com.example.work_with_service.ui.model.pokemons

class PokemonsAttributes(
    val attributes: List<Attribute>
) {

    class Attribute(
        val imageUrl: String,
        val name: String
    )
}