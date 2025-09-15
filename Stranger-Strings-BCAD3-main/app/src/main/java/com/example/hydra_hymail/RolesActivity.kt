package com.example.hydra_hymail


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RolesActivity : AppCompatActivity() {

    private lateinit var btnSelectConsumer: Button
    private lateinit var btnSelectTenant: Button
    private lateinit var tvSkipForNow: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_selector)

        // Hide action bar for clean look
        supportActionBar?.hide()

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        btnSelectConsumer = findViewById(R.id.btnSelectConsumer)
        btnSelectTenant = findViewById(R.id.btnSelectTenant)
        tvSkipForNow = findViewById(R.id.tvSkipForNow)

    }

    private fun setupClickListeners() {
        btnSelectConsumer.setOnClickListener {
            navigateToConsumerLogin()
        }

        btnSelectTenant.setOnClickListener {
            // Navigate to tenant login/flow
            navigateToTenantLogin()
        }

        tvSkipForNow.setOnClickListener {
            // Skip role selection and go to main activity
            navigateToMainActivity("guest")
        }

    }



    private fun navigateToConsumerHome(userType: String) {
        val intent = Intent(this, ConsumerHomeActivity::class.java)
        intent.putExtra("USER_TYPE", userType)
        startActivity(intent)
        finish()

        // Add transition animation
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    private fun navigateToMainActivity(userRole: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("USER_ROLE", userRole)
        startActivity(intent)
        finish()

        // Add transition animation
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    private fun navigateToTenantLogin() {
        val intent = Intent(this, TenantRegistrationActivity::class.java)
        startActivity(intent)
        finish()

        // Add transition animation
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    private fun navigateToConsumerLogin() {
        val intent = Intent(this, ConsumerLoginActivity::class.java)
        startActivity(intent)
        finish()

        // Add transition animation
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }
}



private lateinit var btnSelectConsumer: Button
private lateinit var btnSelectTenant: Button
private lateinit var tvSkipForNow: TextView