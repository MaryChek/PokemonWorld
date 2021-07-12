package com.example.work_with_service.presentation.navigation

interface GoToScreen<T: BaseNavigation> {
    fun goToScreen(destination: T): Any
}