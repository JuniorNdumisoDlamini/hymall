package com.example.hydra_hymail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

class CreatePostActivity : AppCompatActivity() {
    
    private lateinit var etPostContent: EditText
    private lateinit var btnPost: TextView
    private lateinit var btnBack: ImageView
    private lateinit var btnAddMedia: LinearLayout
    private lateinit var mediaPreviewContainer: LinearLayout
    private lateinit var ivMediaPreview: ImageView
    private lateinit var tvMediaName: TextView
    private lateinit var btnRemoveMedia: ImageView
    private lateinit var btnSelectCategory: LinearLayout
    private lateinit var tvSelectedCategory: TextView
    private lateinit var categoryOptions: LinearLayout
    private lateinit var tvDetectedLocation: TextView
    private lateinit var rgPrivacy: RadioGroup
    private lateinit var offlineWarning: LinearLayout
    
    private var selectedMediaUri: Uri? = null
    private var selectedCategory: String = ""
    private var selectedFeeling: String = ""
    private var isOfflineMode = false
    
    // Post data class for offline storage preparation
    data class PostData(
        val id: String = UUID.randomUUID().toString(),
        val content: String,
        val mediaUri: String? = null,
        val category: String,
        val location: String,
        val feeling: String,
        val privacy: String,
        val timestamp: Long = System.currentTimeMillis(),
        val isUploaded: Boolean = false
    )
    
    private val mediaPickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            selectedMediaUri = it
            showMediaPreview(it)
            updatePostButtonState()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_create)
        
        // Hide action bar for clean look
        supportActionBar?.hide()
        
        initializeViews()
        setupClickListeners()
        setupTextWatcher()
        checkNetworkStatus()
        detectLocation()
    }
    
    private fun initializeViews() {
        etPostContent = findViewById(R.id.et_post_content)
        btnPost = findViewById(R.id.btn_post)
        btnBack = findViewById(R.id.btn_back)
        btnAddMedia = findViewById(R.id.btn_add_media)
        mediaPreviewContainer = findViewById(R.id.media_preview_container)
        ivMediaPreview = findViewById(R.id.iv_media_preview)
        tvMediaName = findViewById(R.id.tv_media_name)
        btnRemoveMedia = findViewById(R.id.btn_remove_media)
        btnSelectCategory = findViewById(R.id.btn_select_category)
        tvSelectedCategory = findViewById(R.id.tv_selected_category)
        categoryOptions = findViewById(R.id.category_options)
        tvDetectedLocation = findViewById(R.id.tv_detected_location)
        rgPrivacy = findViewById(R.id.rg_privacy)
        offlineWarning = findViewById(R.id.offline_warning)
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        btnPost.setOnClickListener {
            createPost()
        }
        
        btnAddMedia.setOnClickListener {
            openMediaPicker()
        }
        
        btnRemoveMedia.setOnClickListener {
            removeMedia()
        }
        
        btnSelectCategory.setOnClickListener {
            toggleCategoryOptions()
        }
        
        setupCategoryClickListeners()
        setupEmojiClickListeners()
    }
    
    private fun setupCategoryClickListeners() {
        val categories = mapOf(
            R.id.category_food_dining to "Food & Dining",
            R.id.category_shopping to "Shopping",
            R.id.category_entertainment to "Entertainment",
            R.id.category_facilities to "Facilities",
            R.id.category_events to "Events",
            R.id.category_services to "Services",
            R.id.category_other to "Other"
        )
        
        categories.forEach { (id, category) ->
            findViewById<TextView>(id).setOnClickListener {
                selectCategory(category)
            }
        }
    }
    
    private fun setupEmojiClickListeners() {
        val emojis = mapOf(
            R.id.emoji_happy to "😊",
            R.id.emoji_love to "😍",
            R.id.emoji_angry to "😠",
            R.id.emoji_surprised to "😲",
            R.id.emoji_excited to "🤩",
            R.id.emoji_sad to "😢",
            R.id.emoji_party to "🥳",
            R.id.emoji_thinking to "🤔",
            R.id.emoji_fire to "🔥",
            R.id.emoji_heart to "❤️"
        )
        
        emojis.forEach { (id, emoji) ->
            findViewById<TextView>(id).setOnClickListener {
                selectFeeling(emoji)
                updatePostButtonState()
            }
        }
    }
    
    private fun setupTextWatcher() {
        etPostContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updatePostButtonState()
            }
        })
    }
    
    private fun openMediaPicker() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) 
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, 
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 100)
        } else {
            mediaPickerLauncher.launch("image/*")
        }
    }
    
    private fun showMediaPreview(uri: Uri) {
        mediaPreviewContainer.visibility = View.VISIBLE
        ivMediaPreview.setImageURI(uri)
        tvMediaName.text = "Selected Image"
    }
    
    private fun removeMedia() {
        selectedMediaUri = null
        mediaPreviewContainer.visibility = View.GONE
        updatePostButtonState()
    }
    
    private fun toggleCategoryOptions() {
        categoryOptions.visibility = if (categoryOptions.visibility == View.VISIBLE) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
    
    private fun selectCategory(category: String) {
        selectedCategory = category
        tvSelectedCategory.text = category
        tvSelectedCategory.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
        categoryOptions.visibility = View.GONE
        updatePostButtonState()
    }
    
    private fun selectFeeling(emoji: String) {
        selectedFeeling = emoji
        // Reset all emoji backgrounds
        val emojiIds = listOf(
            R.id.emoji_happy, R.id.emoji_love, R.id.emoji_angry, R.id.emoji_surprised,
            R.id.emoji_excited, R.id.emoji_sad, R.id.emoji_party, R.id.emoji_thinking,
            R.id.emoji_fire, R.id.emoji_heart
        )
        
        emojiIds.forEach { id ->
            findViewById<TextView>(id).setBackgroundResource(R.drawable.search_background)
        }
        
        // Highlight selected emoji
        emojiIds.forEach { id ->
            val emojiView = findViewById<TextView>(id)
            if (emojiView.text.toString() == emoji) {
                emojiView.setBackgroundResource(R.drawable.button_primary)
            }
        }
    }
    
    private fun updatePostButtonState() {
        val hasContent = etPostContent.text.toString().trim().isNotEmpty()
        val hasCategory = selectedCategory.isNotEmpty()
        
        btnPost.isEnabled = hasContent && hasCategory
        btnPost.alpha = if (btnPost.isEnabled) 1.0f else 0.5f
    }
    
    private fun checkNetworkStatus() {
        // TODO: Implement proper network connectivity check
        // For now, simulate offline mode randomly for testing
        isOfflineMode = false // Set to true to test offline mode
        
        if (isOfflineMode) {
            offlineWarning.visibility = View.VISIBLE
        }
    }
    
    private fun detectLocation() {
        // TODO: Implement proper GPS location detection
        // For now, use mock location
        tvDetectedLocation.text = "Auto-detected: Level 1, Main Entrance"
    }
    
    private fun createPost() {
        val content = etPostContent.text.toString().trim()
        val privacy = when (rgPrivacy.checkedRadioButtonId) {
            R.id.rb_public -> "Public"
            R.id.rb_management_only -> "Management Only"
            R.id.rb_anonymous -> "Anonymous"
            else -> "Public"
        }
        
        val postData = PostData(
            content = content,
            mediaUri = selectedMediaUri?.toString(),
            category = selectedCategory,
            location = tvDetectedLocation.text.toString(),
            feeling = selectedFeeling,
            privacy = privacy,
            isUploaded = !isOfflineMode
        )
        
        if (isOfflineMode) {
            // TODO: Save to local database (Room/SQLite)
            savePostOffline(postData)
            Toast.makeText(this, "Post saved offline. Will sync when online.", Toast.LENGTH_LONG).show()
        } else {
            // TODO: Upload to server
            uploadPost(postData)
            Toast.makeText(this, "Post created successfully!", Toast.LENGTH_SHORT).show()
        }
        
        finish()
    }
    
    private fun savePostOffline(postData: PostData) {
        // TODO: Implement Room database save
        // This is where we'll save to local SQLite/Room database
        // The post will be marked as not uploaded and synced later
        println("Saving post offline: $postData")
    }
    
    private fun uploadPost(postData: PostData) {
        // TODO: Implement server upload
        // This is where we'll upload to the server API
        println("Uploading post: $postData")
    }
    
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mediaPickerLauncher.launch("image/*")
        }
    }
}
