package com.example.work_with_service.presentation.viewmodels.base

import com.example.work_with_service.domain.models.Resource
import com.example.work_with_service.presentation.mappers.BasePokemonMapper
import com.example.work_with_service.presentation.models.State
import com.example.work_with_service.presentation.navigation.BaseNavigation

abstract class BaseRefreshableViewModel<
        ModelType : Any,
        NavigationType : BaseNavigation>(
    initModel: ModelType,
    private val mapper: BasePokemonMapper,
) : BaseViewModel<ModelType, NavigationType>(initModel) {

    protected fun <T : Any> getResourceState(resource: Resource<T>): State {
        return mapper.mapToState(resource.status, resource.message)
    }

    abstract fun onButtonRetryConnectionClick()
}