package com.example.work_with_service.presentation.navigation

sealed class FromPokemonDetail: BaseNavigation {
    object PreviousScreen : FromPokemonDetail()
}