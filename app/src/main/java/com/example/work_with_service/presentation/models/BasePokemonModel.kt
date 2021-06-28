package com.example.work_with_service.presentation.models

open class BasePokemonModel(state: State) {
    val isLoadingIndicatorVisible: Boolean = state.isInLoadingState()
    val isConnectionErrorViewVisible: Boolean = state.isInErrorState()
}