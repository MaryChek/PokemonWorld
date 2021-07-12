package com.example.work_with_service.presentation.navigation

import android.os.Bundle

sealed class FromPokemonList : BaseNavigation {
    object PreviousScreen : FromPokemonList()
    class PokemonDetail(val destinationResId: Int, val pokemon: Bundle) : FromPokemonList()
}