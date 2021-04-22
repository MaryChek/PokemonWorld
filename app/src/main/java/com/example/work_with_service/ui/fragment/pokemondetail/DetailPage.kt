package com.example.work_with_service.ui.fragment.pokemondetail

import com.example.work_with_service.ui.model.Pokemon

interface DetailPage {
    fun openDetailedPage(pokemon: Pokemon)
}