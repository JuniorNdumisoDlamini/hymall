package com.example.hydra_hymail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Badge model
data class Badge(
    val title: String,
    val subtitle: String,
    var date: String? = null,
    var isEarned: Boolean = false,
    val iconRes: Int
)

class BadgesActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var btnBadges: Button
    private lateinit var btnLeaderboard: Button
    private lateinit var btnPlayTtt: Button

    private lateinit var badgesView: View
    private lateinit var leaderboardView: View
    private lateinit var tttFragmentContainer: View

    private lateinit var badgesRecycler: RecyclerView
    private lateinit var lockedBadgesRecycler: RecyclerView
    private lateinit var leaderboardRecycler: RecyclerView

    // These must match IDs in your layout:
    private lateinit var tvYourBadges: TextView
    private lateinit var tvLockedBadges: TextView
    private lateinit var tvNoBadgesMessage: TextView

    private lateinit var sampleBadges: MutableList<Badge>
    private lateinit var tttBadge: Badge
    private lateinit var currentUserId: String

    private val leaderboardEntries = mutableListOf<LeaderboardEntry>()
    private lateinit var leaderboardAdapter: LeaderboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badges)

        currentUserId = "userA" // demo user id used for prefs keying (replace with real user id as needed)

        // Views
        btnBack = findViewById(R.id.btn_back)
        btnBadges = findViewById(R.id.btn_badges)
        btnLeaderboard = findViewById(R.id.btn_leaderboard)
        btnPlayTtt = findViewById(R.id.btn_play_ttt)

        badgesView = findViewById(R.id.badges_view)
        leaderboardView = findViewById(R.id.leaderboard_view)
        tttFragmentContainer = findViewById(R.id.tttFragmentContainer)

        badgesRecycler = findViewById(R.id.badges_recycler)
        lockedBadgesRecycler = findViewById(R.id.locked_badges_recycler)
        leaderboardRecycler = findViewById(R.id.leaderboard_recycler)

        // These TextView IDs must exist in your activity_badges.xml:
        tvYourBadges = findViewById(R.id.tv_your_badges)
        tvLockedBadges = findViewById(R.id.tv_locked_badges)
        tvNoBadgesMessage = findViewById(R.id.tv_no_badges_message)

        badgesRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        lockedBadgesRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        leaderboardRecycler.layoutManager = LinearLayoutManager(this)

        sampleBadges = mutableListOf(
            Badge("First Post", "Share your first experience", "Earned 12/1/2024", true, R.drawable.ic_first_post),
            Badge("Coffee Connoisseur", "Post about 5 coffee places", "Earned 12/10/2024", true, R.drawable.ic_coffee),
            Badge("Helpful Helper", "Get 50 helpful reactions", "Earned 12/11/2024", true, R.drawable.ic_thumbs_up),
            Badge("Shopping Guru", "Post about 10 stores", null, false, R.drawable.ic_shopping),
            Badge("Photo Master", "Upload 25 photos", null, false, R.drawable.ic_camera),
            Badge("Tic Tac Toe Champion", "Win your first Tic Tac Toe game", null, false, android.R.drawable.ic_menu_gallery)
        )

        // Load badge state from preferences
        val prefs = getSharedPreferences("badges_prefs", Context.MODE_PRIVATE)
        sampleBadges.forEach { badge ->
            val earnedKey = "${currentUserId}_${badge.title}_earned"
            val dateKey = "${currentUserId}_${badge.title}_date"
            badge.isEarned = prefs.getBoolean(earnedKey, badge.isEarned)
            badge.date = prefs.getString(dateKey, badge.date)
        }

        tttBadge = sampleBadges.first { it.title == "Tic Tac Toe Champion" }

        // Set adapters showing earned / locked
        badgesRecycler.adapter = BadgesAdapter(sampleBadges.filter { it.isEarned }.toMutableList())
        lockedBadgesRecycler.adapter = BadgesAdapter(sampleBadges.filter { !it.isEarned }.toMutableList())

        refreshBadgesVisibility()

        // Load leaderboard from storage (do NOT seed demo users here)
        refreshLeaderboardFromStorage()

        setToggleSelected(true)

        // Button listeners
        btnBadges.setOnClickListener {
            showBadges()
            setToggleSelected(true)
        }

        btnLeaderboard.setOnClickListener {
            showLeaderboard()
            setToggleSelected(false)
        }

        btnPlayTtt.setOnClickListener {
            if (!LeaderboardManager.canPlayRound(this, currentUserId)) {
                val left = LeaderboardManager.getRoundsLeft(this, currentUserId)
                Toast.makeText(this, "No rounds left. Rounds left today: $left", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val left = LeaderboardManager.getRoundsLeft(this, currentUserId)
            Toast.makeText(this, "Rounds left today: $left", Toast.LENGTH_SHORT).show()

            val frag = TicTacToeFragment.newInstance(currentUserId)
            supportFragmentManager.beginTransaction()
                .replace(R.id.tttFragmentContainer, frag)
                .addToBackStack("ttt")
                .commit()

            badgesView.isVisible = false
            leaderboardView.isVisible = false
            tttFragmentContainer.isVisible = true
        }

        btnBack.setOnClickListener { handleBack() }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { handleBack() }
        })
    }

    private fun handleBack() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            tttFragmentContainer.isVisible = false
            showBadges()
            setToggleSelected(true)
        } else finish()
    }

    private fun showBadges() {
        badgesView.isVisible = true
        leaderboardView.isVisible = false
        tttFragmentContainer.isVisible = false
    }

    private fun showLeaderboard() {
        badgesView.isVisible = false
        leaderboardView.isVisible = true
        tttFragmentContainer.isVisible = false
    }

    private fun setToggleSelected(isBadgesSelected: Boolean) {
        val primary = ContextCompat.getColor(this, R.color.primary_color)
        val onPrimary = ContextCompat.getColor(this, android.R.color.white)
        val secondaryText = ContextCompat.getColor(this, R.color.text_secondary)
        val transparent = ContextCompat.getColor(this, android.R.color.transparent)

        if (isBadgesSelected) {
            btnBadges.setBackgroundColor(primary)
            btnBadges.setTextColor(onPrimary)
            btnLeaderboard.setBackgroundColor(transparent)
            btnLeaderboard.setTextColor(secondaryText)
        } else {
            btnLeaderboard.setBackgroundColor(primary)
            btnLeaderboard.setTextColor(onPrimary)
            btnBadges.setBackgroundColor(transparent)
            btnBadges.setTextColor(secondaryText)
        }
    }

    fun unlockTttBadge() {
        if (!tttBadge.isEarned) {
            tttBadge.isEarned = true
            tttBadge.date = "Earned Today"

            val prefs = getSharedPreferences("badges_prefs", Context.MODE_PRIVATE)
            prefs.edit().apply {
                putBoolean("${currentUserId}_${tttBadge.title}_earned", true)
                putString("${currentUserId}_${tttBadge.title}_date", tttBadge.date)
                apply()
            }

            badgesRecycler.adapter = BadgesAdapter(sampleBadges.filter { it.isEarned }.toMutableList())
            lockedBadgesRecycler.adapter = BadgesAdapter(sampleBadges.filter { !it.isEarned }.toMutableList())

            refreshBadgesVisibility()
        }
    }

    private fun refreshBadgesVisibility() {
        val unlocked = sampleBadges.filter { it.isEarned }
        val locked = sampleBadges.filter { !it.isEarned }

        if (unlocked.isEmpty()) {
            badgesRecycler.isVisible = false
            tvYourBadges.isVisible = false
            tvNoBadgesMessage.isVisible = true
        } else {
            badgesRecycler.isVisible = true
            tvYourBadges.isVisible = true
            tvNoBadgesMessage.isVisible = false
        }

        if (locked.isEmpty()) {
            lockedBadgesRecycler.isVisible = false
            tvLockedBadges.isVisible = false
        } else {
            lockedBadgesRecycler.isVisible = true
            tvLockedBadges.isVisible = true
        }
    }

    fun addPointsToLeaderboard(userId: String, deltaPoints: Int) {
        LeaderboardManager.addPoints(this, userId, deltaPoints)
        refreshLeaderboardFromStorage()
        Toast.makeText(this, "Awarded $deltaPoints points to $userId", Toast.LENGTH_SHORT).show()
    }

    fun recordRoundPlayedForUser(userId: String) {
        LeaderboardManager.recordRoundPlayed(this, userId)
        val left = LeaderboardManager.getRoundsLeft(this, userId)
        Toast.makeText(this, "Round recorded. Rounds left today: $left", Toast.LENGTH_SHORT).show()
    }

    @Suppress("unused")
    fun updateLeaderboard(newEntry: LeaderboardEntry) {
        LeaderboardManager.setPoints(this, newEntry.userId, newEntry.points)
        refreshLeaderboardFromStorage()
    }

    @Suppress("unused")
    fun pushScoreToLeaderboard(userId: String) {
        val points = LeaderboardManager.getPoints(this, userId)
        Toast.makeText(this, "$userId has $points points", Toast.LENGTH_SHORT).show()
    }

    /**
     * Refresh leaderboard from storage. Uses DiffUtil to update adapter efficiently
     * (avoids notifyDataSetChanged lint warning).
     */
    private fun refreshLeaderboardFromStorage() {
        val list = LeaderboardManager.getLeaderboard(this)
        leaderboardEntries.clear()
        leaderboardEntries.addAll(list)
        if (::leaderboardAdapter.isInitialized) {
            leaderboardAdapter.updateItems(list)
        } else {
            leaderboardAdapter = LeaderboardAdapter(list.toMutableList())
            leaderboardRecycler.adapter = leaderboardAdapter
        }
    }

    class BadgesAdapter(private val items: MutableList<Badge>) :
        RecyclerView.Adapter<BadgesAdapter.VH>() {

        inner class VH(view: View) : RecyclerView.ViewHolder(view) {
            val icon: ImageView = view.findViewById(R.id.badge_icon)
            val title: TextView = view.findViewById(R.id.badge_title)
            val sub: TextView = view.findViewById(R.id.badge_sub)
            val date: TextView = view.findViewById(R.id.badge_date)
            val status: TextView = view.findViewById(R.id.badge_status)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_badge, parent, false)
            return VH(v)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val b = items[position]
            holder.icon.setImageResource(b.iconRes)
            holder.title.text = b.title
            holder.sub.text = b.subtitle
            holder.date.text = b.date ?: ""
            holder.status.text = if (b.isEarned)
                holder.itemView.context.getString(R.string.badge_status_earned)
            else
                holder.itemView.context.getString(R.string.badge_status_locked)

            val card = holder.itemView as CardView
            val ctx = holder.itemView.context
            val earnedBg = ContextCompat.getColor(ctx, R.color.badge_earned_bg)
            val lockedBg = ContextCompat.getColor(ctx, R.color.disabled_surface)
            card.setCardBackgroundColor(if (b.isEarned) earnedBg else lockedBg)

            holder.itemView.isEnabled = b.isEarned
            holder.itemView.alpha = if (b.isEarned) 1f else 0.8f
        }

        override fun getItemCount(): Int = items.size
    }

    // Simple leaderboard adapter using DiffUtil
    class LeaderboardAdapter(private val items: MutableList<LeaderboardEntry>) :
        RecyclerView.Adapter<LeaderboardAdapter.VH>() {

        inner class VH(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(android.R.id.text1)
            val subtitle: TextView = view.findViewById(android.R.id.text2)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val v = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
            return VH(v)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val e = items[position]
            holder.title.text = e.userId
            holder.subtitle.text = holder.itemView.context.getString(R.string.points_format, e.points)
        }

        override fun getItemCount(): Int = items.size

        fun updateItems(newItems: List<LeaderboardEntry>) {
            val diff = DiffUtil.calculateDiff(LeaderboardDiffCallback(items, newItems))
            items.clear()
            items.addAll(newItems)
            diff.dispatchUpdatesTo(this)
        }

        private class LeaderboardDiffCallback(
            private val oldList: List<LeaderboardEntry>,
            private val newList: List<LeaderboardEntry>
        ) : DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldList.size
            override fun getNewListSize(): Int = newList.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].userId == newList[newItemPosition].userId
            }
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }
        }
    }
}
