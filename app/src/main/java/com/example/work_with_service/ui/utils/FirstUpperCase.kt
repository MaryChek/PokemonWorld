package com.example.work_with_service.ui.utils

import java.util.*

fun firstUpperCase(word: String?): String {
    if (word.isNullOrBlank()) {
        return ""
    }
    return word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1);
}