package com.example.work_with_service.presentation.contract

interface BasePokemonContract {
    interface View {
        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showConnectionErrorMessage()

        fun hideConnectionErrorMessage()
    }

    interface Presenter {
        fun onRetryConnectionClick()
    }
}