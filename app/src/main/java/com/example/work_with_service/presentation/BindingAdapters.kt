package com.example.work_with_service.presentation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.chayangkoon.champ.glide.ktx.load
import com.example.work_with_service.R

    @BindingAdapter("imageUrl")
    fun setImageUrlToSrc(view: ImageView, imageUrl: String?) {
        imageUrl?.let {
            view.load(imageUrl) {
                placeholder(R.mipmap.ic_pokeball)
            }
        }
    }

    @BindingAdapter("android:text")
    fun setListDamageNamesToTextView(view: TextView, names: List<String>) {
        view.text = names.joinToString()
    }

    @BindingAdapter("height")
    fun setLayoutHeight(view: View, height: Float) {
        val layoutParams = view.layoutParams
        layoutParams.height = (height * view.resources.displayMetrics.density).toInt()
        view.layoutParams = layoutParams
        view.invalidate()
    }