package com.example.hydra_hymail.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hydra_hymail.model.User
import com.example.hydra_hymail.model.UserRole

class AuthViewModel : ViewModel() {

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _isAuthenticated = MutableLiveData(false)
    val isAuthenticated: LiveData<Boolean> = _isAuthenticated

    fun login(user: User) {
        _currentUser.value = user
        _isAuthenticated.value = true
    }

    fun logout() {
        _currentUser.value = null
        _isAuthenticated.value = false
    }

    fun setRole(role: UserRole) {
        _currentUser.value = _currentUser.value?.copy(role = role)
    }
}
