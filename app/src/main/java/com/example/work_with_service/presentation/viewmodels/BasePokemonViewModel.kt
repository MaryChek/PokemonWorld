package com.example.work_with_service.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.work_with_service.domain.models.Resource
import com.example.work_with_service.presentation.mappers.BasePokemonMapper
import com.example.work_with_service.presentation.models.State

abstract class BasePokemonViewModel<PokemonModel: Any>(private val mapper: BasePokemonMapper): ViewModel() {

    protected var model = MutableLiveData<PokemonModel>()

    protected fun <T: Any> getResourceState(resource: Resource<T>): State {
        val state: State = mapper.mapToState(resource.status, resource.message)
        errorHandlingIfRequired(state)
        return state
    }

    protected fun errorHandlingIfRequired(state: State) {
        when {
            state.isInErrorState() && state.errorMassage != null ->
                Log.e(null, state.errorMassage)
            state.errorMassage == null ->
                Log.e(null, UNKNOWN_ERROR)
        }
    }

    open fun onButtonRetryConnectionClick() {}

    companion object {
        private const val UNKNOWN_ERROR = "Unknown error"
    }
}