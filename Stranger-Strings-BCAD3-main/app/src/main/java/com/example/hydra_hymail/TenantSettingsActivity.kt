package com.example.hydra_hymail

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner

import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat

class TenantSettingsActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "TenantSettingsActivity"
    }

    private lateinit var btnBack: ImageView
    private lateinit var imgStoreLogo: ImageView
    private lateinit var etStoreName: EditText
    private lateinit var etStoreDescription: EditText
    private lateinit var etContactEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var spinnerStoreCategory: Spinner
    private lateinit var spinnerLanguage: Spinner
    private lateinit var spinnerNotificationSound: Spinner
    private lateinit var switchBiometric: SwitchCompat
    private lateinit var switchOrderNotifications: SwitchCompat
    private lateinit var switchCustomerMessages: SwitchCompat
    private lateinit var switchSystemUpdates: SwitchCompat
    private lateinit var switchDarkMode: SwitchCompat

    // Use View for clickable controls so layout type mismatches don't crash
    private var btnChangePassword: View? = null
    private var btnHelpSupport: View? = null
    private var btnSignOut: View? = null
    private var btnSaveChanges: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenant_settings)

        initViews()
        setupSpinners()
        setupSwitches()
        setupClickListeners()
    }

    private fun initViews() {
        // Required views — keep as explicit types where we need methods from those classes
        btnBack = safeFindViewById(R.id.btn_back) ?: run {
            throw IllegalStateException("Missing required view R.id.btn_back in layout")
        }

        imgStoreLogo = safeFindViewById(R.id.img_store_logo) ?: run {
            Log.e(TAG, "img_store_logo missing — continuing but image actions won't work")
            // create a dummy ImageView to avoid nulls (rare)
            ImageView(this)
        }

        etStoreName = safeFindViewById(R.id.et_store_name) ?: EditText(this)
        etStoreDescription = safeFindViewById(R.id.et_store_description) ?: EditText(this)
        etContactEmail = safeFindViewById(R.id.et_contact_email) ?: EditText(this)
        etPhone = safeFindViewById(R.id.et_phone) ?: EditText(this)

        spinnerStoreCategory = safeFindViewById(R.id.spinner_store_category) ?: Spinner(this)
        spinnerLanguage = safeFindViewById(R.id.spinner_language) ?: Spinner(this)
        spinnerNotificationSound = safeFindViewById(R.id.spinner_notification_sound) ?: Spinner(this)

        // Switches
        switchBiometric = safeFindViewById(R.id.switch_biometric) ?: SwitchCompat(this)
        switchOrderNotifications = safeFindViewById(R.id.switch_order_notifications) ?: SwitchCompat(this)
        switchCustomerMessages = safeFindViewById(R.id.switch_customer_messages) ?: SwitchCompat(this)
        switchSystemUpdates = safeFindViewById(R.id.switch_system_updates) ?: SwitchCompat(this)
        switchDarkMode = safeFindViewById(R.id.switch_dark_mode) ?: SwitchCompat(this)

        // Clickable items: use View? to tolerate layout using LinearLayout/Button/TextView
        btnChangePassword = findViewById(R.id.btn_change_password)
        btnHelpSupport = findViewById(R.id.btn_help_support)
        btnSignOut = findViewById(R.id.btn_sign_out)
        btnSaveChanges = findViewById(R.id.btn_save_changes)

        // diagnostics: log if any clickable IDs are present but not the expected type
        logViewTypeIfMismatch(R.id.btn_change_password, btnChangePassword, "expected clickable (TextView/Button)")
        logViewTypeIfMismatch(R.id.btn_help_support, btnHelpSupport, "expected clickable (TextView/Button)")
        logViewTypeIfMismatch(R.id.btn_sign_out, btnSignOut, "expected clickable (TextView/Button)")
        logViewTypeIfMismatch(R.id.btn_save_changes, btnSaveChanges, "expected clickable (TextView/Button)")
    }

    /** Generic safe find that returns the requested type or null and logs helpful message */
    private inline fun <reified T : View> safeFindViewById(id: Int): T? {
        val v = findViewById<View?>(id) ?: return null
        return if (v is T) {
            v
        } else {
            Log.e(TAG, "View id ${resources.getResourceName(id)} is ${v::class.java.simpleName} but expected ${T::class.java.simpleName}")
            null
        }
    }

    private fun logViewTypeIfMismatch(id: Int, view: View?, expectedNotes: String) {
        if (view == null) {
            Log.w(TAG, "View ${getIdNameSafe(id)} not found in layout.")
            return
        }
        // If it's not a TextView (common mismatch), show a helpful notice
        if (view !is TextView && view !is View) {
            Log.w(TAG, "View ${getIdNameSafe(id)} present but not TextView: ${view::class.java.simpleName}. $expectedNotes")
        } else {
            // good enough — present
        }
    }

    private fun getIdNameSafe(id: Int): String {
        return try {
            resources.getResourceName(id)
        } catch (e: Exception) {
            "id:$id"
        }
    }

    private fun setupSpinners() {
        val categories = listOf("Restaurant", "Retail", "Services", "Entertainment", "Health & Beauty", "Other")
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStoreCategory.adapter = categoryAdapter

        val languages = listOf("English (US)", "Spanish", "French", "German", "Italian", "Portuguese")
        val languageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLanguage.adapter = languageAdapter

        val sounds = listOf("Default", "Chime", "Bell", "Ding", "None")
        val soundAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sounds)
        soundAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNotificationSound.adapter = soundAdapter
    }

    private fun setupSwitches() {
        val primaryColor = ContextCompat.getColor(this, R.color.primary_color)
        val disabledColor = ContextCompat.getColor(this, R.color.text_secondary)

        val states = arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf())
        val colors = intArrayOf(primaryColor, disabledColor)

        val thumbColorStateList = ColorStateList(states, colors)
        val trackColorStateList = ColorStateList(states, colors)

        val switches = arrayOf(
            switchBiometric, switchOrderNotifications, switchCustomerMessages,
            switchSystemUpdates, switchDarkMode
        )

        for (sw in switches) {
            sw.thumbTintList = thumbColorStateList
            sw.trackTintList = trackColorStateList
        }
    }

    private fun setupClickListeners() {
        // Back button — navigate back to TenantDashboardActivity
        btnBack.setOnClickListener {
            val intent = Intent(this, TenantDashboardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        imgStoreLogo.setOnClickListener {
            // TODO: Implement image picker
            Toast.makeText(this, "Change logo tapped", Toast.LENGTH_SHORT).show()
        }

        // For clickable views that might be TextView or a layout container, use safe call
        btnChangePassword?.setOnClickListener {
            // TODO: Navigate to change password screen
            Toast.makeText(this, "Change password clicked", Toast.LENGTH_SHORT).show()
        }

        btnHelpSupport?.setOnClickListener {
            // TODO: Navigate to help & support screen
            Toast.makeText(this, "Help & Support clicked", Toast.LENGTH_SHORT).show()
        }

        btnSignOut?.setOnClickListener {
            // TODO: Implement sign out logic
            Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
        }

        btnSaveChanges?.setOnClickListener {
            saveSettings()
        }

        // Dark mode switch
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            // TODO: Implement dark mode toggle (update theme/prefs)
            Toast.makeText(this, "Dark mode: $isChecked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveSettings() {
        val storeName = etStoreName.text.toString()
        val storeDescription = etStoreDescription.text.toString()
        val contactEmail = etContactEmail.text.toString()
        val phone = etPhone.text.toString()

        // TODO: Save settings to preferences/server (demo toast)
        Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show()

        // For now, just finish (same as original behaviour)
        finish()
    }
}
