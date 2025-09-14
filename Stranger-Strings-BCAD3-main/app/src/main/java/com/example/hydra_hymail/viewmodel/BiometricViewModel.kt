package com.example.hydra_hymail.viewmodel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hydra_hymail.model.BiometricStatus

class BiometricViewModel : ViewModel() {

    private val _biometricStatus = MutableLiveData<BiometricStatus>()
    val biometricStatus: LiveData<BiometricStatus> = _biometricStatus

    fun setBiometricStatus(status: BiometricStatus) {
        _biometricStatus.value = status
    }
}
