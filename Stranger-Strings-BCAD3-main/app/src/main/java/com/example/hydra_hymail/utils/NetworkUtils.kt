package com.example.hydra_hymail.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Utility for checking network connectivity.
 * Useful before making API calls to avoid crashes.
 */
object NetworkUtils {

    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
