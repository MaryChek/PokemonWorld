package com.example.work_with_service.presentation.models

class PokemonListModel(
    val resource: State = State.loading(),
    val pokemons: List<Pokemon>? = null,
) : RefreshableModel(resource)