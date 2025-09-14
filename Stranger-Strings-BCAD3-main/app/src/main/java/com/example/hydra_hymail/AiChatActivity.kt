package com.example.hydra_hymail

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AiChatActivity : AppCompatActivity() {
    
    private lateinit var recyclerChat: RecyclerView
    private lateinit var etMessage: EditText
    private lateinit var btnSend: ImageView
    private lateinit var btnBack: ImageView
    
    // Quick action buttons
    private lateinit var btnStoreHours: TextView
    private lateinit var btnDirections: TextView
    private lateinit var btnCurrentDeals: TextView
    private lateinit var btnEvents: TextView
    
    private val chatMessages = mutableListOf<ChatMessage>()
    private lateinit var chatAdapter: ChatAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ai_chat)
        
        initViews()
        setupRecyclerView()
        setupClickListeners()
        addWelcomeMessage()
    }
    
    private fun initViews() {
        recyclerChat = findViewById(R.id.recycler_chat)
        etMessage = findViewById(R.id.et_message)
        btnSend = findViewById(R.id.btn_send)
        btnBack = findViewById(R.id.btn_back)
        
        btnStoreHours = findViewById(R.id.btn_store_hours)
        btnDirections = findViewById(R.id.btn_directions)
        btnCurrentDeals = findViewById(R.id.btn_current_deals)
        btnEvents = findViewById(R.id.btn_events)
    }
    
    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(chatMessages)
        recyclerChat.adapter = chatAdapter
        recyclerChat.layoutManager = LinearLayoutManager(this)
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        btnSend.setOnClickListener {
            sendMessage()
        }
        
        // Quick action buttons
        btnStoreHours.setOnClickListener {
            handleQuickAction("What are the mall's operating hours?")
        }
        
        btnDirections.setOnClickListener {
            handleQuickAction("I need directions to a store")
        }
        
        btnCurrentDeals.setOnClickListener {
            handleQuickAction("What are the current deals and promotions?")
        }
        
        btnEvents.setOnClickListener {
            handleQuickAction("What events are happening at the mall?")
        }
    }
    
    private fun addWelcomeMessage() {
        val welcomeMessage = ChatMessage(
            message = "Hello! I'm your HyMall AI assistant. How can I help you today? I can help you with store information, directions, promotions, and answer questions about the mall.",
            isUser = false,
            timestamp = System.currentTimeMillis()
        )
        chatMessages.add(welcomeMessage)
        chatAdapter.notifyItemInserted(chatMessages.size - 1)
        recyclerChat.scrollToPosition(chatMessages.size - 1)
    }
    
    private fun sendMessage() {
        val messageText = etMessage.text.toString().trim()
        if (messageText.isNotEmpty()) {
            // Add user message
            val userMessage = ChatMessage(
                message = messageText,
                isUser = true,
                timestamp = System.currentTimeMillis()
            )
            chatMessages.add(userMessage)
            chatAdapter.notifyItemInserted(chatMessages.size - 1)
            
            // Clear input
            etMessage.setText("")
            
            // Process AI response (basic logic for now)
            processAiResponse(messageText)
            
            recyclerChat.scrollToPosition(chatMessages.size - 1)
        }
    }
    
    private fun handleQuickAction(query: String) {
        etMessage.setText(query)
        sendMessage()
    }
    
    private fun processAiResponse(userMessage: String) {
        // Basic response logic - will be replaced with OpenAI integration
        val response = when {
            userMessage.lowercase().contains("direction") || userMessage.lowercase().contains("where") -> {
                "I can help you with directions! Use the Map view in the app to see store locations, or tell me which store you're looking for and I'll guide you there."
            }
            userMessage.lowercase().contains("hours") || userMessage.lowercase().contains("time") -> {
                "The mall is open Monday-Saturday: 9:00 AM - 9:00 PM, Sunday: 10:00 AM - 6:00 PM. Individual store hours may vary."
            }
            userMessage.lowercase().contains("deal") || userMessage.lowercase().contains("promotion") -> {
                "Current promotions include: 20% off at Fashion District, Buy 2 Get 1 Free at selected restaurants, and weekend parking specials!"
            }
            userMessage.lowercase().contains("event") -> {
                "This weekend we have live music at the atrium (2-4 PM), kids' activities at Level 2, and a food festival in the food court!"
            }
            userMessage.lowercase().contains("food") || userMessage.lowercase().contains("restaurant") -> {
                "Our food court has amazing options! Try the new coffee shop on Level 2, or check out our international cuisine section."
            }
            else -> {
                "I'm here to help! You can ask me about store locations, mall hours, current deals, events, or anything else about HyMall."
            }
        }
        
        // Simulate AI thinking delay
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            val aiMessage = ChatMessage(
                message = response,
                isUser = false,
                timestamp = System.currentTimeMillis()
            )
            chatMessages.add(aiMessage)
            chatAdapter.notifyItemInserted(chatMessages.size - 1)
            recyclerChat.scrollToPosition(chatMessages.size - 1)
        }, 1000)
    }
}

// Data class for chat messages
data class ChatMessage(
    val message: String,
    val isUser: Boolean,
    val timestamp: Long
)

// TODO: Implement ChatAdapter for RecyclerView
// TODO: Add OpenAI API integration
// TODO: Add typing indicator animation
// TODO: Add message persistence with Room database
// TODO: Add voice input capability
// TODO: Add emoji reactions to messages
