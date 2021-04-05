package com.example.work_with_service.ui.model.pokiattributes

class ListPokemonAttributes(
    val listAttributes: List<Attributes>
) : PokiAttributes {

    class Attributes(
        val imageUrl: String,
        val name: String
    )
}