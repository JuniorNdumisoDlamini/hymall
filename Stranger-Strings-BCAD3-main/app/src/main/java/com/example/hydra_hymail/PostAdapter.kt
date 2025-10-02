package com.example.hydra_hymail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

data class Post(
    val id: String,
    val author: String,
    val category: String,
    val content: String,
    val location: String,
    val mediaUrl: String? = null
)

class PostAdapter(
    private val posts: MutableList<Post>,
    private val onHideClick: (Post) -> Unit,
    private val onDeleteClick: (Post) -> Unit,
    private val onEmojiClick: (Post, String) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val authorText: TextView = itemView.findViewById(R.id.post_author)
        val categoryText: TextView = itemView.findViewById(R.id.post_category)
        val contentText: TextView = itemView.findViewById(R.id.post_content)
        val locationText: TextView = itemView.findViewById(R.id.post_location)
        val mediaImage: ImageView = itemView.findViewById(R.id.post_media)
        val hideButton: Button = itemView.findViewById(R.id.btn_hide)
        val deleteButton: Button = itemView.findViewById(R.id.btn_delete)
        val emojiHappy: TextView = itemView.findViewById(R.id.emoji_happy)
        val emojiLove: TextView = itemView.findViewById(R.id.emoji_love)
        val emojiAngry: TextView = itemView.findViewById(R.id.emoji_angry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post_tenant, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.authorText.text = post.author
        holder.categoryText.text = post.category
        holder.contentText.text = post.content
        holder.locationText.text = post.location

        // Load media using Coil
        if (!post.mediaUrl.isNullOrEmpty()) {
            holder.mediaImage.visibility = View.VISIBLE
            holder.mediaImage.load(post.mediaUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder) // fallback drawable
                error(R.drawable.ic_placeholder)
            }
        } else {
            holder.mediaImage.visibility = View.GONE
        }

        holder.hideButton.setOnClickListener {
            onHideClick(post)
            notifyItemChanged(position)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClick(post)
            posts.removeAt(position)
            notifyItemRemoved(position)
        }

        holder.emojiHappy.setOnClickListener { onEmojiClick(post, "😊") }
        holder.emojiLove.setOnClickListener { onEmojiClick(post, "😍") }
        holder.emojiAngry.setOnClickListener { onEmojiClick(post, "😠") }
    }

    override fun getItemCount(): Int = posts.size
}
