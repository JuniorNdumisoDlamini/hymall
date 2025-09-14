package com.example.hydra_hymail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuViewModel : ViewModel() {

    private val _isMenuOpen = MutableLiveData(false)
    val isMenuOpen: LiveData<Boolean> = _isMenuOpen

    private val _selectedMenuItem = MutableLiveData<String?>()
    val selectedMenuItem: LiveData<String?> = _selectedMenuItem

    fun toggleMenu() {
        _isMenuOpen.value = !_isMenuOpen.value!!
    }

    fun selectMenuItem(item: String) {
        _selectedMenuItem.value = item
    }

    fun closeMenu() {
        _isMenuOpen.value = false
    }
}
