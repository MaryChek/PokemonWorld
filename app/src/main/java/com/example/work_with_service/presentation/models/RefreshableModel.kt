package com.example.work_with_service.presentation.models

import com.example.work_with_service.domain.models.Resource
import com.example.work_with_service.domain.models.Resource.Companion.isInErrorState
import com.example.work_with_service.domain.models.Resource.Companion.isInLoadingState

interface RefreshableModel<ResourceType> {
    val resource: Resource<ResourceType>?

    fun isInLoadingState(): Boolean =
        resource?.loading == true

    fun isInErrorState(): Boolean =
        resource?.error == true

    fun isLoadedSuccessfully(): Boolean =
        resource?.isFinishedSuccessfully ?: false


    val isLoadingIndicatorVisible: Boolean = resource.isInLoadingState()
    val isConnectionErrorViewVisible: Boolean = resource.isInErrorState()
}