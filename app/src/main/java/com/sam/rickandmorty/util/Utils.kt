package com.sam.rickandmorty.util

import android.content.Context
import android.view.View
import com.sam.rickandmorty.R

fun checkAndSetStatusIcon(statusIcon: View, status: String, context: Context) {
    when (status) {
        "Alive" -> {
            statusIcon.backgroundTintList =
                context.applicationContext.getColorStateList(R.color.alive)
        }
        "Dead" -> {
            statusIcon.backgroundTintList =
                context.applicationContext.getColorStateList(R.color.dead)
        }
        "unknown" -> {
            statusIcon.backgroundTintList =
                context.applicationContext.getColorStateList(R.color.unknown)
        }
    }
}