package com.example.work_with_service.presentation.models

class PokemonListModel(
    status: Status = Status.loading()
){
    val isLoadingIndicatorVisible: Boolean = status.isInLoadingState()
    val isConnectionErrorViewVisible: Boolean = status.isInErrorState()
}