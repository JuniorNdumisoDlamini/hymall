package com.example.hydra_hymail.data.local



import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hydra_hymail.model.Settings

@Dao
interface SettingsDao {
    @Insert
    suspend fun save(settings: Settings)

    @Query("SELECT * FROM settings LIMIT 1")
    suspend fun load(): Settings?
}
