package com.example.work_with_service.presentation.fragments

import android.view.View
import androidx.fragment.app.Fragment

open class BasePokemonFragment : Fragment() {

    protected fun View.updateVisibility(show: Boolean) {
        val visibility =
            when (show) {
                true -> View.VISIBLE
                else -> View.GONE
            }
        this.visibility = visibility
    }
}