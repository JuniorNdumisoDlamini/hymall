package com.example.hydra_hymail.data.repository



import com.example.hydra_hymail.model.Settings

class SettingsRepository {
    fun saveSettings(settings: Settings) {
        // TODO: Save to local storage or cloud
    }

    fun loadSettings(): Settings {
        // TODO: Load from local storage
        return Settings()
    }
}
