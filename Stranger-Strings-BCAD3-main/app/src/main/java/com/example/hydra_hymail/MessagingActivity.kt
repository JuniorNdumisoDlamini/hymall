package com.example.hydra_hymail



import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hydra_hymail.R

class MessagingActivity : AppCompatActivity() {

    private lateinit var recyclerMessages: RecyclerView
    private lateinit var etMessage: EditText
    private lateinit var btnSend: ImageView
    private lateinit var btnCall: ImageView
    private lateinit var btnBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)

        initViews()
        setupRecyclerView()
        setupClickListeners()
    }

    private fun initViews() {
        recyclerMessages = findViewById(R.id.recycler_messages)
        etMessage = findViewById(R.id.et_message)
        btnSend = findViewById(R.id.btn_send)
        btnCall = findViewById(R.id.btn_call)
        btnBack = findViewById(R.id.btn_back)
    }

    private fun setupRecyclerView() {
        recyclerMessages.layoutManager = LinearLayoutManager(this)
        // TODO: Set up message adapter
        // recyclerMessages.adapter = MessageAdapter(messageList)
    }

    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        btnSend.setOnClickListener {
            sendMessage()
        }

        btnCall.setOnClickListener {
            initiateCall()
        }
    }

    private fun sendMessage() {
        val messageText = etMessage.text.toString().trim()
        if (messageText.isNotEmpty()) {
            // TODO: Send message to mall management
            etMessage.text.clear()
        }
    }

    private fun initiateCall() {
        // Call mall management support number
        val phoneNumber = "tel:+1234567890" // Replace with actual support number
        val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber))
        startActivity(callIntent)
    }
}
