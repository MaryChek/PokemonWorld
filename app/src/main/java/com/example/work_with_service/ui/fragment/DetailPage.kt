package com.example.work_with_service.ui.fragment

import com.example.work_with_service.data.model.Pokemon

interface DetailPage {
    fun openDetailedPage(pokemon: Pokemon)
}