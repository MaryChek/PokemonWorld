package com.example.work_with_service.ui.model

class PagerTitlesModel(private var titles: MutableList<String>) {

    fun getTitleByPosition(position: Int): String? =
        titles.getOrNull(position)

    fun setTitleByPosition(position: Int, title: String) =
        titles.add(position, title)
}