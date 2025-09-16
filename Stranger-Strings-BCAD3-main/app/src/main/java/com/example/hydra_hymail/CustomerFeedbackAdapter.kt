package com.example.hydra_hymail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class CustomerFeedbackAdapter(
    private val onReplyClick: (CustomerFeedback) -> Unit
) : RecyclerView.Adapter<CustomerFeedbackAdapter.FeedbackViewHolder>() {

    private var feedbackList = listOf<CustomerFeedback>()

    fun updateFeedback(newFeedback: List<CustomerFeedback>) {
        feedbackList = newFeedback
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_customer_feedback, parent, false)
        return FeedbackViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        holder.bind(feedbackList[position])
    }

    override fun getItemCount() = feedbackList.size

    inner class FeedbackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCustomerName: TextView = itemView.findViewById(R.id.tv_customer_name)
        private val tvFeedbackCategory: TextView = itemView.findViewById(R.id.tv_feedback_category)
        private val tvFeedbackTime: TextView = itemView.findViewById(R.id.tv_feedback_time)
        private val tvRatingStars: TextView = itemView.findViewById(R.id.tv_rating_stars)
        private val tvRatingNumber: TextView = itemView.findViewById(R.id.tv_rating_number)
        private val tvFeedbackContent: TextView = itemView.findViewById(R.id.tv_feedback_content)
        private val layoutReplySection: LinearLayout = itemView.findViewById(R.id.layout_reply_section)
        private val tvManagementReply: TextView = itemView.findViewById(R.id.tv_management_reply)
        private val tvReplyTime: TextView = itemView.findViewById(R.id.tv_reply_time)
        private val btnReply: Button = itemView.findViewById(R.id.btn_reply)
        private val btnMarkResolved: Button = itemView.findViewById(R.id.btn_mark_resolved)

        fun bind(feedback: CustomerFeedback) {
            tvCustomerName.text = feedback.customerName
            tvFeedbackCategory.text = feedback.category
            tvFeedbackTime.text = feedback.timeAgo
            tvFeedbackContent.text = feedback.content
            tvRatingNumber.text = feedback.rating.toString()

            // Set rating stars
            val stars = "⭐".repeat(feedback.rating.toInt())
            tvRatingStars.text = stars

            // Show/hide reply section
            if (feedback.hasReply) {
                layoutReplySection.visibility = View.VISIBLE
                tvManagementReply.text = feedback.replyText
                tvReplyTime.text = "Replied ${feedback.replyTime}"
                btnReply.text = "Edit Reply"
            } else {
                layoutReplySection.visibility = View.GONE
                btnReply.text = "Reply"
            }

            // Set click listeners
            btnReply.setOnClickListener {
                onReplyClick(feedback)
            }

            btnMarkResolved.setOnClickListener {
                // TODO: Implement mark as resolved functionality
                Toast.makeText(itemView.context, "Marked as resolved", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
