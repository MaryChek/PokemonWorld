package com.example.work_with_service.presentation.viewmodels.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.work_with_service.presentation.navigation.GoToScreen
import com.example.work_with_service.presentation.navigation.BaseNavigation
import com.example.work_with_service.presentation.views.controller.SingleEventLiveData

abstract class BaseViewModel<ModelType : Any, NavigationType: BaseNavigation>(
    initModel: ModelType,
) : ViewModel(), GoToScreen<NavigationType> {

    protected var model = initModel

    private val navigationEvent: MutableLiveData<NavigationType> = SingleEventLiveData()

    val modelUpdated = MutableLiveData<ModelType>()
    val navigationUpdated: LiveData<NavigationType> = navigationEvent

    protected fun updateScreen() {
        modelUpdated.value = model
    }

    override fun goToScreen(destination: NavigationType) {
        navigationEvent.value = destination
    }

    abstract fun goToPrevious()
}