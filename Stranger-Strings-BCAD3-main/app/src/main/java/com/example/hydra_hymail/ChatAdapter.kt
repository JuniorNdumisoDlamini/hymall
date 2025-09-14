package com.example.hydra_hymail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

class ChatAdapter(private val messages: List<ChatMessage>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_USER = 1
        private const val VIEW_TYPE_AI = 2
    }

    override fun getItemViewType(position: Int): Int =
        if (messages[position].isUser) VIEW_TYPE_USER else VIEW_TYPE_AI

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_USER) {
            UserMessageViewHolder(inflater.inflate(R.layout.item_user_message, parent, false))
        } else {
            AiMessageViewHolder(inflater.inflate(R.layout.item_ai_message, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when (holder) {
            is UserMessageViewHolder -> holder.bind(message)
            is AiMessageViewHolder -> holder.bind(message)
        }
    }

    override fun getItemCount(): Int = messages.size

    class UserMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.tv_message)
        private val timeText: TextView = itemView.findViewById(R.id.tv_time)
        fun bind(message: ChatMessage) {
            messageText.text = message.message
            timeText.text = formatTime(message.timestamp)
        }
    }

    class AiMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.tv_message)
        private val timeText: TextView = itemView.findViewById(R.id.tv_time)
        fun bind(message: ChatMessage) {
            messageText.text = message.message
            timeText.text = formatTime(message.timestamp)
        }
    }
}
