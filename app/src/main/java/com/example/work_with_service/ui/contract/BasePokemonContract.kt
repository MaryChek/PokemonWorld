package com.example.work_with_service.ui.contract

import com.example.work_with_service.ui.model.PokemonInfo

interface BasePokemonContract {
    interface View {
        fun showLoadingIndicator()

        fun hideLoadingIndicator()
    }

    interface Presenter {
        fun onViewRestart()
    }
}