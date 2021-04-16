package com.example.work_with_service.ui.model

import com.example.work_with_service.ui.mapper.BasePokemonMapper

class PagerTitlesModel(private var titles: MutableList<String>) {
    private val pokemonMapper = BasePokemonMapper()

    fun getTitleByPosition(position: Int): String? =
        titles.getOrNull(position)

    fun setTitleByPosition(position: Int, title: String) =
        titles.add(position, pokemonMapper.firstUpperCase(title))
}