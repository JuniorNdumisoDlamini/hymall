package com.example.hydra_hymail

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hydra_hymail.R

class StoreManagementActivity : AppCompatActivity() {

    private lateinit var recyclerFeedback: RecyclerView
    private lateinit var spinnerFeedbackFilter: Spinner
    private lateinit var spinnerCategoryFilter: Spinner
    private lateinit var chipAllFeedback: TextView
    private lateinit var chipGoodFeedback: TextView
    private lateinit var chipBadFeedback: TextView
    private lateinit var chipNeedsReply: TextView

    private lateinit var feedbackAdapter: CustomerFeedbackAdapter
    private var currentFilter = "all"
    private var currentCategory = "all"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_management)

        initViews()
        setupSpinners()
        setupRecyclerView()
        setupClickListeners()
        setupFilterChips()
    }

    private fun initViews() {
        recyclerFeedback = findViewById(R.id.recycler_feedback)
        spinnerFeedbackFilter = findViewById(R.id.spinner_feedback_filter)
        spinnerCategoryFilter = findViewById(R.id.spinner_category_filter)
        chipAllFeedback = findViewById(R.id.chip_all_feedback)
        chipGoodFeedback = findViewById(R.id.chip_good_feedback)
        chipBadFeedback = findViewById(R.id.chip_bad_feedback)
        chipNeedsReply = findViewById(R.id.chip_needs_reply)
    }

    private fun setupSpinners() {
        // Category filter spinner
        val categories = arrayOf("All Categories", "Food & Dining", "Shopping", "Entertainment", "Facilities", "Events", "Services", "Other")
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoryFilter.adapter = categoryAdapter

        // Feedback type filter
        val feedbackTypes = arrayOf("All Feedback", "Recent", "High Priority", "Unresolved")
        val feedbackAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, feedbackTypes)
        feedbackAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFeedbackFilter.adapter = feedbackAdapter

        // Set up listeners
        spinnerCategoryFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                currentCategory = if (position == 0) "all" else categories[position].lowercase()
                filterFeedback()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerFeedbackFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                // Handle feedback type filtering
                filterFeedback()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupRecyclerView() {
        recyclerFeedback.layoutManager = LinearLayoutManager(this)
        feedbackAdapter = CustomerFeedbackAdapter { feedback ->
            showReplyDialog(feedback)
        }
        recyclerFeedback.adapter = feedbackAdapter

        // Load sample feedback data
        loadFeedbackData()
    }

    private fun setupFilterChips() {
        chipAllFeedback.setOnClickListener {
            selectFilterChip("all")
        }

        chipGoodFeedback.setOnClickListener {
            selectFilterChip("good")
        }

        chipBadFeedback.setOnClickListener {
            selectFilterChip("bad")
        }

        chipNeedsReply.setOnClickListener {
            selectFilterChip("needs_reply")
        }
    }

    private fun selectFilterChip(filter: String) {
        currentFilter = filter

        // Reset all chips
        resetChipStyles()

        // Highlight selected chip
        when (filter) {
            "all" -> {
                chipAllFeedback.setBackgroundResource(R.drawable.chip_selected)
                chipAllFeedback.setTextColor(getColor(R.color.white))
            }
            "good" -> {
                chipGoodFeedback.setBackgroundResource(R.drawable.chip_selected)
                chipGoodFeedback.setTextColor(getColor(R.color.white))
            }
            "bad" -> {
                chipBadFeedback.setBackgroundResource(R.drawable.chip_selected)
                chipBadFeedback.setTextColor(getColor(R.color.white))
            }
            "needs_reply" -> {
                chipNeedsReply.setBackgroundResource(R.drawable.chip_selected)
                chipNeedsReply.setTextColor(getColor(R.color.white))
            }
        }

        filterFeedback()
    }

    private fun resetChipStyles() {
        val chips = listOf(chipAllFeedback, chipGoodFeedback, chipBadFeedback, chipNeedsReply)
        chips.forEach { chip ->
            chip.setBackgroundResource(R.drawable.chip_unselected)
            chip.setTextColor(getColor(R.color.text_secondary))
        }
    }

    private fun filterFeedback() {
        // TODO: Implement actual filtering logic based on currentFilter and currentCategory
        // This would filter the feedback list and update the adapter
    }

    private fun showReplyDialog(feedback: CustomerFeedback) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_reply_feedback)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val tvOriginalFeedback = dialog.findViewById<TextView>(R.id.tv_original_feedback)
        val etReplyMessage = dialog.findViewById<EditText>(R.id.et_reply_message)
        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel_reply)
        val btnSend = dialog.findViewById<Button>(R.id.btn_send_reply)

        // Quick reply templates
        val templateThankYou = dialog.findViewById<TextView>(R.id.template_thank_you)
        val templateApologize = dialog.findViewById<TextView>(R.id.template_apologize)
        val templateFollowUp = dialog.findViewById<TextView>(R.id.template_follow_up)

        tvOriginalFeedback.text = feedback.content

        // Template click listeners
        templateThankYou.setOnClickListener {
            etReplyMessage.setText(templateThankYou.text)
        }
        templateApologize.setOnClickListener {
            etReplyMessage.setText(templateApologize.text)
        }
        templateFollowUp.setOnClickListener {
            etReplyMessage.setText(templateFollowUp.text)
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnSend.setOnClickListener {
            val replyText = etReplyMessage.text.toString().trim()
            if (replyText.isNotEmpty()) {
                sendReply(feedback, replyText)
                dialog.dismiss()
                Toast.makeText(this, "Reply sent successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a reply message", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun sendReply(feedback: CustomerFeedback, replyText: String) {
        // TODO: Implement actual reply sending logic
        // This would send the reply to the backend and update the feedback item
        feedback.hasReply = true
        feedback.replyText = replyText
        feedback.replyTime = "Just now"
        feedbackAdapter.notifyDataSetChanged()
    }

    private fun loadFeedbackData() {
        // TODO: Load actual feedback data from backend
        // For now, using sample data
        val sampleFeedback = listOf(
            CustomerFeedback("John Doe", "Great service and amazing food!", "Food & Dining", 5.0f, "2 hours ago"),
            CustomerFeedback("Jane Smith", "The facilities could be better maintained.", "Facilities", 2.0f, "4 hours ago"),
            CustomerFeedback("Mike Johnson", "Excellent shopping experience!", "Shopping", 4.5f, "1 day ago")
        )
        feedbackAdapter.updateFeedback(sampleFeedback)
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

data class CustomerFeedback(
    val customerName: String,
    val content: String,
    val category: String,
    val rating: Float,
    val timeAgo: String,
    var hasReply: Boolean = false,
    var replyText: String = "",
    var replyTime: String = "",
    val imageUrl: String? = null
)
