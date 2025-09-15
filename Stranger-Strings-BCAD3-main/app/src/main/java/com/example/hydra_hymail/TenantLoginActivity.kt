package com.example.hydra_hymail


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TenantLoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignInTenant: Button
    private lateinit var btnGoogleLogin: Button
    private lateinit var btnAppleLogin: Button
    private lateinit var tvContactSupport: TextView
    private lateinit var tvNeedAccount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenant_login)

        // Hide action bar for clean look
        supportActionBar?.hide()

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnSignInTenant = findViewById(R.id.btnSignInTenant)
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin)
        btnAppleLogin = findViewById(R.id.btnAppleLogin)
        tvContactSupport = findViewById(R.id.tvContactSupport)
        tvNeedAccount = findViewById(R.id.tvNeedAccount)
    }

    private fun setupClickListeners() {
        btnSignInTenant.setOnClickListener {
            // TODO: Implement tenant authentication logic
            navigateToTenantHome()
        }

        btnGoogleLogin.setOnClickListener {
            // TODO: Implement Google Sign-In for tenants
            navigateToTenantHome()
        }

        btnAppleLogin.setOnClickListener {
            // TODO: Implement Apple Sign-In for tenants
            navigateToTenantHome()
        }

        tvNeedAccount.setOnClickListener {
            val intent = Intent(this, TenantRegistrationActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        tvContactSupport.setOnClickListener {
            // TODO: Open support contact options
        }
    }

    private fun navigateToTenantHome() {
        val intent = Intent(this, TenantDashboardActivity::class.java)
        intent.putExtra("USER_ROLE", "tenant")
        startActivity(intent)
        finish()

        // Add transition animation
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }
}
