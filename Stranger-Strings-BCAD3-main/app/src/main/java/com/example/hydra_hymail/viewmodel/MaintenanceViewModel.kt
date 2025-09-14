package com.example.hydra_hymail.viewmodel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hydra_hymail.model.MaintenanceIssue

class MaintenanceViewModel : ViewModel() {

    private val _issues = MutableLiveData<List<MaintenanceIssue>>(emptyList())
    val issues: LiveData<List<MaintenanceIssue>> = _issues

    fun addIssue(issue: MaintenanceIssue) {
        _issues.value = _issues.value?.plus(issue)
    }

    fun updateIssue(updated: MaintenanceIssue) {
        _issues.value = _issues.value?.map {
            if (it.id == updated.id) updated else it
        }
    }
}
