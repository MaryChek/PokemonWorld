package com.example.work_with_service.presentation.fragments.base

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.annotation.CallSuper
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.work_with_service.presentation.navigation.BaseNavigation
import com.example.work_with_service.presentation.viewmodels.base.BaseRefreshableViewModel
import com.example.work_with_service.presentation.viewmodels.base.BaseViewModel

abstract class RefreshableViewModelFragment< // RefreshableViewModel
        Model: Any,
        NavigationType : BaseNavigation,
        ViewModel : BaseRefreshableViewModel<Model, NavigationType>>
    : BaseViewModelFragment<Model, NavigationType, ViewModel>() {

    protected var buttonRetryConnection: Button? = null
    protected var progressIndicator: ProgressBar? = null
    protected var connectionError: ConstraintLayout? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnRetryConnectionClickListener()
    }

    private fun setOnRetryConnectionClickListener() {
        buttonRetryConnection?.setOnClickListener {
            viewModel.onButtonRetryConnectionClick()
        }
    }

    @CallSuper
    override fun updateScreen(model: Model) {
        progressIndicator?.updateVisibility(model.isLoadingIndicatorVisible)
        connectionError?.updateVisibility(model.isConnectionErrorViewVisible)
    }

    protected fun View.updateVisibility(show: Boolean) {
        val visibility =
            when (show) {
                true -> View.VISIBLE
                else -> View.GONE
            }
        this.visibility = visibility
    }

    override fun onDestroy() {
        super.onDestroy()
        buttonRetryConnection = null

    }
}