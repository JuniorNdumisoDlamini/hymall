package com.example.hydra_hymail

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hydra_hymail.R

class StoreManagementActivity : AppCompatActivity() {

    private lateinit var recyclerFeedback: RecyclerView
    private lateinit var spinnerFeedbackFilter: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_management)

        initViews()
        setupRecyclerView()
        setupClickListeners()
    }

    private fun initViews() {
        recyclerFeedback = findViewById(R.id.recycler_feedback)
        spinnerFeedbackFilter = findViewById(R.id.spinner_feedback_filter)
    }

    private fun setupRecyclerView() {
        recyclerFeedback.layoutManager = LinearLayoutManager(this)
        // TODO: Set up feedback adapter
        // recyclerFeedback.adapter = FeedbackAdapter(feedbackList)
    }

    private fun setupClickListeners() {
        val btnBack = findViewById<ImageView>(R.id.btn_back)
        val btnCreatePost = findViewById<Button>(R.id.btn_create_post)
        val btnViewAnalytics = findViewById<Button>(R.id.btn_view_analytics)

        btnBack.setOnClickListener { finish() }

        btnCreatePost.setOnClickListener {
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
        }

        btnViewAnalytics.setOnClickListener {
            // TODO: Navigate to detailed analytics view
        }
    }
}
