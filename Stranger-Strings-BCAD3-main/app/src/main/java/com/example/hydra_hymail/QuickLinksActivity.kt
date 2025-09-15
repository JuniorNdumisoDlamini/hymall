package com.example.hydra_hymail



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.hydra_hymail.R

class QuickLinksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_links)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        val btnBack = findViewById<ImageView>(R.id.btn_back)
        val btnStartOnboarding = findViewById<Button>(R.id.btn_start_onboarding)

        // Quick action cards
        val cardManageStore = findViewById<LinearLayout>(R.id.card_manage_store)
        val cardViewAnalytics = findViewById<LinearLayout>(R.id.card_view_analytics)
        val cardCustomerFeedback = findViewById<LinearLayout>(R.id.card_customer_feedback)
        val cardContactSupport = findViewById<LinearLayout>(R.id.card_contact_support)

        // Onboarding steps
        val stepSetupProfile = findViewById<LinearLayout>(R.id.step_setup_profile)
        val stepManagePosts = findViewById<LinearLayout>(R.id.step_manage_posts)
        val stepUnderstandAnalytics = findViewById<LinearLayout>(R.id.step_understand_analytics)

        btnBack.setOnClickListener { finish() }

        btnStartOnboarding.setOnClickListener {
            // TODO: Start onboarding flow
        }

        cardManageStore.setOnClickListener {
            val intent = Intent(this, StoreManagementActivity::class.java)
            startActivity(intent)
        }

        cardViewAnalytics.setOnClickListener {
            val intent = Intent(this, StoreManagementActivity::class.java)
            startActivity(intent)
        }

        cardCustomerFeedback.setOnClickListener {
            val intent = Intent(this, StoreManagementActivity::class.java)
            startActivity(intent)
        }

        cardContactSupport.setOnClickListener {
            val intent = Intent(this, MessagingActivity::class.java)
            startActivity(intent)
        }

        // Onboarding step click listeners
        stepSetupProfile.setOnClickListener {
            // TODO: Navigate to profile setup
        }

        stepManagePosts.setOnClickListener {
            // TODO: Navigate to post management tutorial
        }

        stepUnderstandAnalytics.setOnClickListener {
            // TODO: Navigate to analytics tutorial
        }
    }
}
