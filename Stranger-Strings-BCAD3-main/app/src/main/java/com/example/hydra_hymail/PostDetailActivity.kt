package com.example.hydra_hymail

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class PostDetailActivity : AppCompatActivity() {
    
    private var isLiked = false
    private var isLoved = false
    private var likeCount = 12
    private var loveCount = 8
    private var commentCount = 3
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
        
        // Hide action bar for clean look
        supportActionBar?.hide()
        
        setupClickListeners()
        setupReactionButtons()
        setupCommentSystem()
    }
    
    private fun setupClickListeners() {
        val btnBack = findViewById<ImageView>(R.id.btn_back)
        val btnMoreOptions = findViewById<ImageView>(R.id.btn_more_options)
        
        btnBack.setOnClickListener {
            finish()
        }
        
        btnMoreOptions.setOnClickListener {
            showMoreOptionsDialog()
        }
    }
    
    private fun setupReactionButtons() {
        val btnLike = findViewById<LinearLayout>(R.id.btn_like)
        val btnLove = findViewById<LinearLayout>(R.id.btn_love)
        val btnShare = findViewById<LinearLayout>(R.id.btn_share)
        
        val likeCountText = findViewById<TextView>(R.id.like_count)
        val loveCountText = findViewById<TextView>(R.id.love_count)
        
        btnLike.setOnClickListener {
            isLiked = !isLiked
            if (isLiked) {
                likeCount++
                Toast.makeText(this, "👍 Liked!", Toast.LENGTH_SHORT).show()
            } else {
                likeCount--
                Toast.makeText(this, "Like removed", Toast.LENGTH_SHORT).show()
            }
            likeCountText.text = likeCount.toString()
        }
        
        btnLove.setOnClickListener {
            isLoved = !isLoved
            if (isLoved) {
                loveCount++
                Toast.makeText(this, "❤️ Loved!", Toast.LENGTH_SHORT).show()
            } else {
                loveCount--
                Toast.makeText(this, "Love removed", Toast.LENGTH_SHORT).show()
            }
            loveCountText.text = loveCount.toString()
        }
        
        btnShare.setOnClickListener {
            sharePost()
        }
    }
    
    private fun setupCommentSystem() {
        val commentInput = findViewById<EditText>(R.id.comment_input)
        val btnSendComment = findViewById<ImageView>(R.id.btn_send_comment)
        val commentsContainer = findViewById<LinearLayout>(R.id.comments_container)
        val commentCountText = findViewById<TextView>(R.id.comment_count)
        
        btnSendComment.setOnClickListener {
            val commentText = commentInput.text.toString().trim()
            if (commentText.isNotEmpty()) {
                addComment(commentText, commentsContainer)
                commentInput.text.clear()
                commentCount++
                commentCountText.text = commentCount.toString()
                Toast.makeText(this, "Comment added!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a comment", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun addComment(commentText: String, container: LinearLayout) {
        val commentLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 32
            }
        }
        
        // User avatar
        val avatar = ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(96, 96).apply {
                marginEnd = 32
            }
            setImageResource(R.drawable.ic_user_avatar)
        }
        
        // Comment content
        val contentLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }
        
        val userName = TextView(this).apply {
            text = "You"
            textSize = 12f
            setTextColor(resources.getColor(R.color.text_primary, null))
            setTypeface(null, android.graphics.Typeface.BOLD)
        }
        
        val comment = TextView(this).apply {
            text = commentText
            textSize = 14f
            setTextColor(resources.getColor(R.color.text_primary, null))
            setPadding(0, 8, 0, 0)
        }
        
        val timeStamp = TextView(this).apply {
            text = "Just now"
            textSize = 10f
            setTextColor(resources.getColor(R.color.text_secondary, null))
            setPadding(0, 16, 0, 0)
        }
        
        contentLayout.addView(userName)
        contentLayout.addView(comment)
        contentLayout.addView(timeStamp)
        
        commentLayout.addView(avatar)
        commentLayout.addView(contentLayout)
        
        container.addView(commentLayout, 0) // Add at top
    }
    
    private fun sharePost() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Check out this amazing post from HyMall!")
            putExtra(Intent.EXTRA_SUBJECT, "HyMall Post")
        }
        startActivity(Intent.createChooser(shareIntent, "Share post via"))
    }
    
    private fun showMoreOptionsDialog() {
        val options = arrayOf("Report Post", "Block User", "Copy Link", "Save Post")
        
        AlertDialog.Builder(this)
            .setTitle("Post Options")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showReportDialog()
                    1 -> {
                        Toast.makeText(this, "User blocked", Toast.LENGTH_SHORT).show()
                    }
                    2 -> {
                        Toast.makeText(this, "Link copied to clipboard", Toast.LENGTH_SHORT).show()
                    }
                    3 -> {
                        Toast.makeText(this, "Post saved", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .show()
    }
    
    private fun showReportDialog() {
        val reportReasons = arrayOf(
            "Spam or misleading",
            "Inappropriate content", 
            "Harassment or bullying",
            "Violence or dangerous content",
            "Copyright infringement",
            "Other"
        )
        
        AlertDialog.Builder(this)
            .setTitle("Report Post")
            .setItems(reportReasons) { _, which ->
                val reason = reportReasons[which]
                Toast.makeText(this, "Post reported for: $reason", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
