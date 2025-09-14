package com.example.hydra_hymail.utils


import android.view.View

/**
 * Common extension functions for Kotlin Android.
 * Helps keep code in Activities/Fragments cleaner.
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}
