package com.example.hydra_hymail.data.local



import androidx.room.Dao
import androidx.room.Insert
import com.example.hydra_hymail.model.MaintenanceIssue

@Dao
interface MaintenanceDao {
    @Insert
    suspend fun log(issue: MaintenanceIssue)
}
