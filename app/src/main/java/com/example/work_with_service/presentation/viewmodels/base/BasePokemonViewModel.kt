package com.example.work_with_service.presentation.viewmodels.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.work_with_service.domain.models.Resource
import com.example.work_with_service.presentation.mappers.BasePokemonMapper
import com.example.work_with_service.presentation.models.State
import com.example.work_with_service.presentation.navigation.GoToScreen
import com.example.work_with_service.presentation.navigation.BaseNavigation
import com.example.work_with_service.presentation.views.controller.SingleEventLiveData

abstract class BasePokemonViewModel<PokemonModel : Any, Navigation: BaseNavigation>(
    initModel: PokemonModel,
    private val mapper: BasePokemonMapper
) : ViewModel(), GoToScreen<Navigation> {

    protected var model = initModel

    private val navigationEvent: MutableLiveData<Navigation> = SingleEventLiveData()

    val modelUpdated = MutableLiveData<PokemonModel>()
    val navigationUpdated: LiveData<Navigation> = navigationEvent

    protected fun updateScreen() {
        modelUpdated.value = model
    }

    override fun goToScreen(destination: Navigation) {
        navigationEvent.value = destination
    }

    protected fun <T : Any> getResourceState(resource: Resource<T>): State {
        val state: State = mapper.mapToState(resource.status, resource.message)
        errorHandlingIfRequired(state)
        return state
    }

    protected fun errorHandlingIfRequired(state: State) {
        when {
            state.isInErrorState() && state.errorMassage != null ->
                Log.e(null, state.errorMassage)
            state.isInErrorState() && state.errorMassage == null ->
                Log.e(null, UNKNOWN_ERROR)
        }
    }

    open fun onButtonRetryConnectionClick() {}

    abstract fun goToPrevious()

    companion object {
        private const val UNKNOWN_ERROR = "Unknown error"
    }
}