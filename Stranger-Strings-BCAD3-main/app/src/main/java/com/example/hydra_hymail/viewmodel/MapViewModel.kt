package com.example.hydra_hymail.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hydra_hymail.model.LocationData

class MapViewModel : ViewModel() {

    private val _selectedLocation = MutableLiveData<LocationData?>()
    val selectedLocation: LiveData<LocationData?> = _selectedLocation

    fun setLocation(location: LocationData) {
        _selectedLocation.value = location
    }
}
