package com.example.work_with_service.presentation.fragments.base

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.work_with_service.App
import com.example.work_with_service.presentation.navigation.BaseNavigation
import com.example.work_with_service.presentation.navigation.GoToScreen
import com.example.work_with_service.presentation.viewmodels.base.BaseViewModel

abstract class BaseViewModelFragment<
        Model : Any,
        NavigationType : BaseNavigation,
        ViewModel : BaseViewModel<Model, NavigationType>>
    : Fragment(), GoToScreen<NavigationType> {

    protected lateinit var viewModel: ViewModel
    protected lateinit var navController: NavController
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        val app: App = (requireActivity().applicationContext as App)
        val viewModelFactory = app.viewModelFactory
        val viewModelProvider = ViewModelProvider(this, viewModelFactory)
        viewModel = viewModelProvider.get(getViewModelClass())
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
//        initOnBackPressedListener()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.navigationUpdated.observe(viewLifecycleOwner, Observer(this::goToScreen))
        viewModel.modelUpdated.observe(viewLifecycleOwner, Observer(this::updateScreen))
    }

    private fun initOnBackPressedListener() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.goToPrevious()
            }
        }
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    protected fun navigate(@IdRes navigateToId: Int, arguments: Bundle? = null) =
        navController.navigate(navigateToId, arguments)

    protected open fun navigateToPrevious() {
        navController.popBackStack()
    }

    abstract fun updateScreen(model: Model)

    abstract fun getViewModelClass(): Class<ViewModel>
}