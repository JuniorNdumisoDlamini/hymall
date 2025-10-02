package com.example.hydra_hymail

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.ImageView
import android.widget.TextView
import android.widget.EditText

class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Hide action bar for clean look (we use our custom header)
        supportActionBar?.hide()

        // Initialize SharedPreferences for settings storage
        sharedPreferences = getSharedPreferences("HyMallSettings", MODE_PRIVATE)

        setupHeader()
        setupLanguageSpinner()
        setupNotificationSoundSpinner()
        setupSwitches()
        setupClickListeners()
        loadSavedSettings()
    }

    private fun setupHeader() {
        val btnBack = findViewById<ImageView>(R.id.btn_back)
        btnBack.setOnClickListener {
            // Explicitly go back to ConsumerHomeActivity to match navigation expectations
            try {
                val intent = Intent(this, ConsumerHomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                finish()
            }
        }
    }

    private fun setupLanguageSpinner() {
        val spinnerLanguage = findViewById<Spinner>(R.id.spinner_language)
        val languages = arrayOf("English", "Zulu", "Afrikaans")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLanguage.adapter = adapter
    }

    private fun setupNotificationSoundSpinner() {
        val spinnerSound = findViewById<Spinner>(R.id.spinner_notification_sound)
        val sounds = arrayOf("Default", "Chime", "Bell", "Notification", "Alert", "None")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sounds)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSound.adapter = adapter
    }

    private fun setupSwitches() {
        val switchDarkMode = findViewById<SwitchCompat>(R.id.switch_dark_mode)
        val switchBiometric = findViewById<SwitchCompat>(R.id.switch_biometric)
        val switchNewPosts = findViewById<SwitchCompat>(R.id.switch_new_posts)
        val switchComments = findViewById<SwitchCompat>(R.id.switch_comments)
        val switchReactions = findViewById<SwitchCompat>(R.id.switch_reactions)
        val switchManagement = findViewById<SwitchCompat>(R.id.switch_management)
        val switchMarketing = findViewById<SwitchCompat>(R.id.switch_marketing)

        // Set listeners before loading saved values (loadSavedSettings will set initial checked state)
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            sharedPreferences.edit().putBoolean("dark_mode", isChecked).apply()
        }

        switchBiometric.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("biometric_login", isChecked).apply()
            Toast.makeText(this, if (isChecked) "Biometric login enabled" else "Biometric login disabled", Toast.LENGTH_SHORT).show()
        }

        switchNewPosts.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("notify_new_posts", isChecked).apply()
        }

        switchComments.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("notify_comments", isChecked).apply()
        }

        switchReactions.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("notify_reactions", isChecked).apply()
        }

        switchManagement.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("notify_management", isChecked).apply()
        }

        switchMarketing.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("notify_marketing", isChecked).apply()
        }
    }

    private fun setupClickListeners() {
        val btnChangePassword = findViewById<TextView>(R.id.btn_change_password)
        val btnOfflinePosts = findViewById<LinearLayout>(R.id.btn_offline_posts)
        val btnAboutHelp = findViewById<LinearLayout>(R.id.btn_about_help)
        val btnSignOut = findViewById<LinearLayout>(R.id.btn_sign_out)
        val btnSaveChanges = findViewById<TextView>(R.id.btn_save_changes)
        val btnProfileImage = findViewById<ImageView?>(R.id.img_profile)

        btnChangePassword.setOnClickListener {
            Toast.makeText(this, "Change password functionality coming soon", Toast.LENGTH_SHORT).show()
        }

        btnOfflinePosts.setOnClickListener {
            val intent = Intent(this, OfflinePostsActivity::class.java)
            startActivity(intent)
        }

        btnAboutHelp.setOnClickListener {
            val intent = Intent(this, AboutHelpActivity::class.java)
            startActivity(intent)
        }

        btnSignOut.setOnClickListener {
            // Clear user session and navigate to login/main
            sharedPreferences.edit().clear().apply()
            val intent = Intent(this, ConsumerLoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        btnSaveChanges.setOnClickListener {
            saveAllSettings()
            Toast.makeText(this, "Settings saved successfully", Toast.LENGTH_SHORT).show()
        }

        // optional: tap profile image to change (placeholder)
        btnProfileImage?.setOnClickListener {
            Toast.makeText(this, "Change photo tapped (placeholder)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadSavedSettings() {
        // switches
        findViewById<SwitchCompat>(R.id.switch_dark_mode).isChecked = sharedPreferences.getBoolean("dark_mode", false)
        findViewById<SwitchCompat>(R.id.switch_biometric).isChecked = sharedPreferences.getBoolean("biometric_login", true)
        findViewById<SwitchCompat>(R.id.switch_new_posts).isChecked = sharedPreferences.getBoolean("notify_new_posts", true)
        findViewById<SwitchCompat>(R.id.switch_comments).isChecked = sharedPreferences.getBoolean("notify_comments", true)
        findViewById<SwitchCompat>(R.id.switch_reactions).isChecked = sharedPreferences.getBoolean("notify_reactions", true)
        findViewById<SwitchCompat>(R.id.switch_management).isChecked = sharedPreferences.getBoolean("notify_management", true)
        findViewById<SwitchCompat>(R.id.switch_marketing).isChecked = sharedPreferences.getBoolean("notify_marketing", false)

        // text fields
        findViewById<EditText>(R.id.et_display_name).setText(sharedPreferences.getString("display_name", "John Doe"))
        findViewById<EditText>(R.id.et_email).setText(sharedPreferences.getString("email", "john@gmail.com"))

        // spinner selections (if saved)
        val spinnerLanguage = findViewById<Spinner>(R.id.spinner_language)
        val savedLanguage = sharedPreferences.getString("language", null)
        if (!savedLanguage.isNullOrEmpty()) {
            setSpinnerSelection(spinnerLanguage, savedLanguage)
        }

        val spinnerSound = findViewById<Spinner>(R.id.spinner_notification_sound)
        val savedSound = sharedPreferences.getString("notification_sound", null)
        if (!savedSound.isNullOrEmpty()) {
            setSpinnerSelection(spinnerSound, savedSound)
        }
    }

    private fun setSpinnerSelection(spinner: Spinner, value: String) {
        val adapter = spinner.adapter ?: return
        for (i in 0 until adapter.count) {
            val item = adapter.getItem(i)?.toString()
            if (item.equals(value, ignoreCase = true)) {
                spinner.setSelection(i)
                return
            }
        }
    }

    private fun saveAllSettings() {
        val etDisplayName = findViewById<EditText>(R.id.et_display_name)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val spinnerLanguage = findViewById<Spinner>(R.id.spinner_language)
        val spinnerSound = findViewById<Spinner>(R.id.spinner_notification_sound)

        val editor = sharedPreferences.edit()
        editor.putString("display_name", etDisplayName.text.toString())
        editor.putString("email", etEmail.text.toString())
        editor.putString("language", spinnerLanguage.selectedItem?.toString() ?: "")
        editor.putString("notification_sound", spinnerSound.selectedItem?.toString() ?: "")
        editor.apply()
    }
}
