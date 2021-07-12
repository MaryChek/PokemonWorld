package com.example.work_with_service.presentation.models

class PokemonListModel(
    val state: State = State.loading(),
    val pokemons: List<Pokemon>? = null,
) : BasePokemonModel(state)