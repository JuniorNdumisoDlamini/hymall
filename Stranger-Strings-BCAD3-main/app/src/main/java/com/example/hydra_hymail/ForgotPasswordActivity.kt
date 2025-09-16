package com.example.hydra_hymail



import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var etResetEmail: EditText
    private lateinit var btnResetPassword: Button
    private lateinit var tvBackToLogin: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Hide action bar for clean look
        supportActionBar?.hide()

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        ivBack = findViewById(R.id.ivBack)
        etResetEmail = findViewById(R.id.etResetEmail)
        btnResetPassword = findViewById(R.id.btnResetPassword)
        tvBackToLogin = findViewById(R.id.tvBackToLogin)
    }

    private fun setupClickListeners() {
        ivBack.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        btnResetPassword.setOnClickListener {
            val email = etResetEmail.text.toString().trim()

            if (email.isEmpty()) {
                etResetEmail.error = "Please enter your email address"
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etResetEmail.error = "Please enter a valid email address"
                return@setOnClickListener
            }

            // TODO: Implement actual password reset logic
            sendPasswordResetEmail(email)
        }

        tvBackToLogin.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
    }

    private fun sendPasswordResetEmail(email: String) {
        // TODO: Implement actual password reset API call
        Toast.makeText(this, "Password reset link sent to $email", Toast.LENGTH_LONG).show()

        // Navigate back to login after successful reset request
        finish()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }
}
