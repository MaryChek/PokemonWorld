package com.example.work_with_service.ui.contract

interface BasePokemonContract {
    interface View {
        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showConnectionErrorMessage()

        fun hideConnectionErrorMessage()
    }

    interface Presenter {
//        fun onViewRestart()

        fun onRetryConnectionClick()
    }
}