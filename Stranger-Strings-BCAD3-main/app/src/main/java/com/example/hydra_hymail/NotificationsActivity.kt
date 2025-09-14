package com.example.hydra_hymail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NotificationsActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var btnMarkAllRead: ImageView
    private lateinit var tabAll: TextView
    private lateinit var tabMentions: TextView
    private lateinit var tabLikes: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        initializeViews()
        setupClickListeners()
        setupTabs()
    }

    private fun initializeViews() {
        btnBack = findViewById(R.id.btn_back)
        btnMarkAllRead = findViewById(R.id.btn_mark_all_read)
        tabAll = findViewById(R.id.tab_all)
        tabMentions = findViewById(R.id.tab_mentions)
        tabLikes = findViewById(R.id.tab_likes)
    }

    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        btnMarkAllRead.setOnClickListener {
            // TODO: Implement mark all as read functionality
            // This will update notification status in database
        }
    }

    private fun setupTabs() {
        tabAll.setOnClickListener {
            selectTab(tabAll)
            loadAllNotifications()
        }

        tabMentions.setOnClickListener {
            selectTab(tabMentions)
            loadMentionNotifications()
        }

        tabLikes.setOnClickListener {
            selectTab(tabLikes)
            loadLikeNotifications()
        }
    }

    private fun selectTab(selectedTab: TextView) {
        // Reset all tabs
        resetTabStyles()
        
        // Highlight selected tab
        selectedTab.setTextColor(getColor(R.color.primary))
        selectedTab.setBackgroundResource(R.drawable.tab_selected_background)
    }

    private fun resetTabStyles() {
        val tabs = listOf(tabAll, tabMentions, tabLikes)
        tabs.forEach { tab ->
            tab.setTextColor(getColor(R.color.text_secondary))
            tab.setBackgroundResource(android.R.color.transparent)
        }
    }

    private fun loadAllNotifications() {
        // TODO: Load all notifications from database
        // This will include likes, comments, mentions, system notifications
    }

    private fun loadMentionNotifications() {
        // TODO: Load only mention notifications
        // Filter notifications where user was mentioned
    }

    private fun loadLikeNotifications() {
        // TODO: Load only like notifications
        // Filter notifications for post likes and reactions
    }
}
