package com.example.hydra_hymail.utils

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

/**
 * Utility for handling biometric authentication (fingerprint/face unlock).
 * This is called in the login flow after SSO or when re-validating sensitive actions.
 */
object BiometricUtils {

    fun isBiometricAvailable(context: Context): Boolean {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) ==
                BiometricManager.BIOMETRIC_SUCCESS
    }

    fun showBiometricPrompt(
        context: Context,
        title: String,
        subtitle: String,
        description: String,
        onAuthSuccess: () -> Unit,
        onAuthError: (String) -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(context)
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setDescription(description)
            .setNegativeButtonText("Cancel")
            .build()

        val biometricPrompt = BiometricPrompt(
            (context as androidx.fragment.app.FragmentActivity),
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    onAuthSuccess()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    onAuthError(errString.toString())
                }
            }
        )

        biometricPrompt.authenticate(promptInfo)
    }
}
