package com.example.hydra_hymail.viewmodel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hydra_hymail.model.Badge

class CommunityViewModel : ViewModel() {

    private val _badges = MutableLiveData<List<Badge>>(emptyList())
    val badges: LiveData<List<Badge>> = _badges

    fun addBadge(badge: Badge) {
        _badges.value = _badges.value?.plus(badge)
    }
}
