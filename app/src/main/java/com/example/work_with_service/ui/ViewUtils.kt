package com.example.work_with_service.ui

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.work_with_service.R
import java.util.*

fun firstUpperCase(word: String?): String {
    if (word.isNullOrBlank()) {
        return ""
    }
    return word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1);
}

fun setImageWithGlide(with: View, resource: String, into: ImageView) {
    Glide.with(with)
        .asBitmap()
        .load(resource)
        .transform(CircleCrop())
        .placeholder(R.mipmap.ic_launcher_foreground)
        .into(into)
}