package com.example.hydra_hymail.viewmodel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hydra_hymail.model.Settings
import com.example.hydra_hymail.model.User

class ProfileViewModel : ViewModel() {

    private val _userProfile = MutableLiveData<User?>()
    val userProfile: LiveData<User?> = _userProfile

    fun setProfile(user: User) {
        _userProfile.value = user
    }

    fun updateSettings(settings: Settings) {
        _userProfile.value = _userProfile.value?.copy(settings = settings)
    }
}
