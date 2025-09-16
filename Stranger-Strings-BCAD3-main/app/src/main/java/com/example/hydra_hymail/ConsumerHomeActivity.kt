package com.example.hydra_hymail

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ConsumerHomeActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumer_home)
        
        // Hide action bar for clean look
        supportActionBar?.hide()
        
        // Get login method from intent
        val loginMethod = intent.getStringExtra("LOGIN_METHOD") ?: "unknown"
        
        setupHeaderNavigation() // Added header navigation setup
        setupBottomNavigation()
        setupPostClickListeners()
        setupAiChatbot()
        
        // TODO: Initialize user session based on login method
        // TODO: Set up feed refresh functionality
    }
    
    private fun setupHeaderNavigation() {
        val btnNotifications = findViewById<ImageView>(R.id.btn_notifications)
        val btnQuickActions = findViewById<ImageView>(R.id.btn_quick_actions)
        val btnProfile = findViewById<ImageView>(R.id.btn_profile)
        
        btnNotifications.setOnClickListener {
            val intent = Intent(this, NotificationsActivity::class.java)
            startActivity(intent)
        }
        
        btnQuickActions.setOnClickListener {
            // TODO: Show quick actions bottom sheet or menu
            Toast.makeText(this, "Quick actions coming soon", Toast.LENGTH_SHORT).show()
        }
        
        btnProfile.setOnClickListener {
            // TODO: Show profile menu or navigate to profile settings
            Toast.makeText(this, "Profile menu coming soon", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun setupAiChatbot() {
        val fabAiChat = findViewById<FloatingActionButton>(R.id.fab_ai_chat)
        fabAiChat.setOnClickListener {
            val intent = Intent(this, AiChatActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun setupPostClickListeners() {
        // Find all post containers and add click listeners
        val scrollView = findViewById<ScrollView>(R.id.feed_scroll)
        val feedContainer = scrollView.getChildAt(0) as LinearLayout
        
        // Add click listeners to post containers
        for (i in 0 until feedContainer.childCount) {
            val postView = feedContainer.getChildAt(i)
            if (postView is LinearLayout) {
                postView.setOnClickListener {
                    // Navigate to post detail
                    val intent = Intent(this, PostDetailActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
    
    private fun setupBottomNavigation() {
        val homeNav = findViewById<LinearLayout>(R.id.nav_home)
        val mapNav = findViewById<LinearLayout>(R.id.nav_map)
        val postNav = findViewById<LinearLayout>(R.id.nav_post)
        val badgesNav = findViewById<LinearLayout>(R.id.nav_badges)
        val settingsNav = findViewById<LinearLayout>(R.id.nav_settings)
        
        homeNav.setOnClickListener {
            Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show()
            // TODO: Navigate to home fragment/activity
        }
        
        mapNav.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
        
        postNav.setOnClickListener {
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
        }
        
        badgesNav.setOnClickListener {
            val intent = Intent(this, BadgesActivity::class.java)
            startActivity(intent)
        }
        
        settingsNav.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
