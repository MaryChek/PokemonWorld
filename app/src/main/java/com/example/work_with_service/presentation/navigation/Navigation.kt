package com.example.work_with_service.presentation.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import java.io.Serializable

class Navigation(
    @IdRes val navigateToId: Int,
    val arguments: Bundle?
) : Serializable