package com.example.hydra_hymail



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TenantRegistrationActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etStoreName: EditText
    private lateinit var etStoreNumber: EditText
    private lateinit var etDepartment: EditText
    private lateinit var btnRegisterTenant: Button
    private lateinit var btnGoogleLogin: Button
    private lateinit var btnAppleLogin: Button
    private lateinit var tvContactSupport: TextView
    private lateinit var tvAlreadyHaveAccount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenant_registration)

        // Hide action bar for clean look
        supportActionBar?.hide()

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etStoreName = findViewById(R.id.etStoreName)
        etStoreNumber = findViewById(R.id.etStoreNumber)
        etDepartment = findViewById(R.id.etDepartment)
        btnRegisterTenant = findViewById(R.id.btnRegisterTenant)
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin)
        btnAppleLogin = findViewById(R.id.btnAppleLogin)
        tvContactSupport = findViewById(R.id.tvContactSupport)
        tvAlreadyHaveAccount = findViewById(R.id.tvAlreadyHaveAccount)
    }

    private fun setupClickListeners() {
        btnRegisterTenant.setOnClickListener {
            // TODO: Implement tenant registration logic
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

        tvAlreadyHaveAccount.setOnClickListener {
            val intent = Intent(this, TenantLoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        tvContactSupport.setOnClickListener {
            // TODO: Open support contact options
        }
    }

    private fun navigateToTenantHome() {
        val intent = Intent(this, QuickLinksActivity::class.java)
        intent.putExtra("USER_ROLE", "tenant")
        startActivity(intent)
        finish()

        // Add transition animation
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }
}
