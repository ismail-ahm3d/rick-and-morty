package com.sam.rickandmorty.util

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import com.sam.rickandmorty.databinding.ResourceEventBinding

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

/**
 * These Three functions below are to handle events coming from the state flow
 */
fun handleSuccess(binding: ResourceEventBinding) {

    failureViewsVisibility(
        false,
        binding.textFailure,
        binding.buttonFailure
    )
    binding.progressBar.isVisible = false
}

fun handleLoading(binding: ResourceEventBinding) {

    failureViewsVisibility(
        false,
        binding.textFailure,
        binding.buttonFailure
    )
    binding.progressBar.isVisible = true
}

fun handleFailure(binding: ResourceEventBinding) {

    failureViewsVisibility(
        true,
        binding.textFailure,
        binding.buttonFailure
    )
    binding.progressBar.isVisible = false
}