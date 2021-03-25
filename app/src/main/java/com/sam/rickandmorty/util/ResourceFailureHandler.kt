package com.sam.rickandmorty.util

import android.opengl.Visibility
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible

fun failureViewsVisibility(
    isVisible: Boolean,
    textFailure: TextView,
    retryButton: Button
) {

    if (!isVisible) {
        textFailure.visibility = View.GONE
        retryButton.visibility = View.GONE
    } else {
        textFailure.isVisible = isVisible
        retryButton.isVisible = isVisible
    }
}