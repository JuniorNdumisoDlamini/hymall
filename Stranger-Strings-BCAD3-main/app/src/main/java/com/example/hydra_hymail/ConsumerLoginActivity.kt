package com.example.hydra_hymail

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ConsumerLoginActivity : AppCompatActivity() {
    
    private lateinit var btnGoogleLogin: Button
    private lateinit var btnAppleLogin: Button
    private lateinit var btnBiometricLogin: Button
    private lateinit var btnGuestLogin: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumer_login)
        
        // Hide action bar for clean look
        supportActionBar?.hide()
        
        initViews()
        setupClickListeners()
    }
    
    private fun initViews() {
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin)
        btnAppleLogin = findViewById(R.id.btnAppleLogin)
        btnBiometricLogin = findViewById(R.id.btnBiometricLogin)
        btnGuestLogin = findViewById(R.id.btnGuestLogin)
    }
    
    private fun setupClickListeners() {
        btnGoogleLogin.setOnClickListener {
            navigateToConsumerHome("google")
        }
        
        btnAppleLogin.setOnClickListener {
            navigateToConsumerHome("apple")
        }
        
        btnBiometricLogin.setOnClickListener {
            navigateToConsumerHome("biometric")
        }
        
        btnGuestLogin.setOnClickListener {
            navigateToConsumerHome("guest")
        }
    }
    
    private fun navigateToConsumerHome(loginMethod: String) {
        val intent = Intent(this, ConsumerHomeActivity::class.java)
        intent.putExtra("LOGIN_METHOD", loginMethod)
        startActivity(intent)
        finish()
        
        // Add transition animation
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }
}
