package com.example.hydra_hymail






import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class Badge(
    val title: String,
    val subtitle: String,
    val date: String?,
    val isEarned: Boolean,
    val iconRes: Int
)

class BadgesActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var btnBadges: Button
    private lateinit var btnLeaderboard: Button

    private lateinit var badgesView: View
    private lateinit var leaderboardView: View

    private lateinit var badgesRecycler: RecyclerView
    private lateinit var lockedBadgesRecycler: RecyclerView
    private lateinit var leaderboardRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badges)

        // views
        btnBack = findViewById(R.id.btn_back)
        btnBadges = findViewById(R.id.btn_badges)
        btnLeaderboard = findViewById(R.id.btn_leaderboard)

        badgesView = findViewById(R.id.badges_view)
        leaderboardView = findViewById(R.id.leaderboard_view)

        badgesRecycler = findViewById(R.id.badges_recycler)
        lockedBadgesRecycler = findViewById(R.id.locked_badges_recycler)
        leaderboardRecycler = findViewById(R.id.leaderboard_recycler)

        // layout managers
        badgesRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        lockedBadgesRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        leaderboardRecycler.layoutManager = LinearLayoutManager(this)

        // sample data
        val sampleBadges = listOf(
            Badge("First Post", "Share your first experience", "Earned 12/1/2024", true, R.drawable.ic_first_post),
            Badge("Coffee Connoisseur", "Post about 5 different coffee places", "Earned 12/10/2024", true, R.drawable.ic_coffee),
            Badge("Helpful Helper", "Get 50 helpful reactions", "Earned 12/11/2024", true, R.drawable.ic_thumbs_up),
            Badge("Shopping Guru", "Post about 10 different stores", null, false, R.drawable.ic_shopping),
            Badge("Photo Master", "Upload 25 photos", null, false, R.drawable.ic_camera)
        )

        // adapters
        val earnedAdapter = BadgesAdapter(sampleBadges.filter { it.isEarned }.toMutableList())
        val lockedAdapter = BadgesAdapter(sampleBadges.filter { !it.isEarned }.toMutableList())
        val leaderboardAdapter = LeaderboardAdapter(sampleBadges.mapIndexed { index, badge ->
            // convert to a leaderboard entry (temporary)
            LeaderboardEntry("User ${index+1}", (500 - index * 10))
        }.toMutableList())

        badgesRecycler.adapter = earnedAdapter
        lockedBadgesRecycler.adapter = lockedAdapter
        leaderboardRecycler.adapter = leaderboardAdapter

        // toggle initial styles
        setToggleSelected(true)

        btnBadges.setOnClickListener {
            showBadges()
            setToggleSelected(true)
        }

        btnLeaderboard.setOnClickListener {
            showLeaderboard()
            setToggleSelected(false)
        }

        btnBack.setOnClickListener {
            // navigate to CustomerHomeActivity (replace with your actual class)
            val intent = Intent(this, ConsumerHomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showBadges() {
        badgesView.visibility = View.VISIBLE
        leaderboardView.visibility = View.GONE
    }

    private fun showLeaderboard() {
        badgesView.visibility = View.GONE
        leaderboardView.visibility = View.VISIBLE
    }

    private fun setToggleSelected(isBadgesSelected: Boolean) {
        val primary = ContextCompat.getColor(this, R.color.primary_color)
        val onPrimary = ContextCompat.getColor(this, android.R.color.white)
        val secondaryText = ContextCompat.getColor(this, R.color.text_secondary)
        val transparent = ContextCompat.getColor(this, android.R.color.transparent)

        if (isBadgesSelected) {
            // badges selected
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

    // Adapter for badges
    class BadgesAdapter(private val items: MutableList<Badge>) :
        RecyclerView.Adapter<BadgesAdapter.VH>() {

        inner class VH(view: View) : RecyclerView.ViewHolder(view) {
            val icon = view.findViewById<ImageView>(R.id.badge_icon)
            val title = view.findViewById<TextView>(R.id.badge_title)
            val sub = view.findViewById<TextView>(R.id.badge_sub)
            val date = view.findViewById<TextView>(R.id.badge_date)
            val status = view.findViewById<TextView>(R.id.badge_status)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_badge, parent, false)
            return VH(v)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val b = items[position]

            // safe placeholder if icon not found - replace with real drawables
            try {
                holder.icon.setImageResource(b.iconRes)
            } catch (e: Exception) {
                holder.icon.setImageResource(android.R.drawable.ic_menu_gallery)
            }
            holder.title.text = b.title
            holder.sub.text = b.subtitle
            holder.date.text = b.date ?: ""
            holder.status.text = if (b.isEarned) "Earned" else "Locked"

            // card background tint depending on earned state
            val card = holder.itemView as CardView
            val ctx = holder.itemView.context
            val earnedBg = ContextCompat.getColor(ctx, R.color.badge_earned_bg)
            val lockedBg = ContextCompat.getColor(ctx, R.color.disabled_surface)
            card.setCardBackgroundColor(if (b.isEarned) earnedBg else lockedBg)

            // disable clicks for locked
            holder.itemView.isEnabled = b.isEarned
            holder.itemView.alpha = if (b.isEarned) 1f else 0.8f
        }

        override fun getItemCount(): Int = items.size
    }

    // Simple leaderboard entry + adapter (demo)
    data class LeaderboardEntry(val name: String, val score: Int)

    class LeaderboardAdapter(private val items: MutableList<LeaderboardEntry>) :
        RecyclerView.Adapter<LeaderboardAdapter.VH>() {

        inner class VH(view: View) : RecyclerView.ViewHolder(view) {
            val tvName: TextView = view.findViewById(android.R.id.text1)
            val tvScore: TextView = view.findViewById(android.R.id.text2)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val v = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_2, parent, false)
            return VH(v)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val entry = items[position]
            holder.tvName.text = "${position + 1}. ${entry.name}"
            holder.tvScore.text = "${entry.score} XP"
        }

        override fun getItemCount(): Int = items.size
    }
}
