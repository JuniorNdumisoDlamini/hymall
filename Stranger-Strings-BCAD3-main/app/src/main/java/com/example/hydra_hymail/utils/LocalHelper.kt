package com.example.hydra_hymail.utils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

/**
 * Handles switching between languages (English, Zulu, Afrikaans).
 * This lets the app dynamically switch language in Settings.
 */
object LocaleHelper {

    fun setLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}
