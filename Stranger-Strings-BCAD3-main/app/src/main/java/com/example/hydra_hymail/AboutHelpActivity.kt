package com.example.hydra_hymail

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AboutHelpActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_help)
        
        // Hide action bar for clean look
        supportActionBar?.hide()
        
        setupHeader()
    }
    
    private fun setupHeader() {
        val btnBack = findViewById<ImageView>(R.id.btn_back)
        btnBack.setOnClickListener {
            finish()
        }
    }
}
