package com.example.hydra_hymail.services

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

/**
 * Service wrapper to fetch device location.
 * We use this to auto-tag posts with GPS coordinates and mall names.
 */
class LocationService(context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getLastLocation(onResult: (Location?) -> Unit) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            onResult(location)
        }.addOnFailureListener {
            onResult(null)
        }
    }
}
