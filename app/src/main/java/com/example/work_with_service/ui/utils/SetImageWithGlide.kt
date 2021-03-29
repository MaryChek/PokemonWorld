package com.example.work_with_service.ui.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.work_with_service.R

fun setImageWithGlide(with: View, resource: String?, into: ImageView) =
    Glide.with(with)
        .asBitmap()
        .load(resource ?: R.mipmap.ic_pokeball_foreground)
        .transform(CircleCrop())
        .placeholder(R.mipmap.ic_pokeball_foreground)
        .into(into)