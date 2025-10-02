package com.example.hydra_hymail

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load


class TenantDashboardActivity : AppCompatActivity() {

    private var btnCategoryFilter: View? = null
    private var tvSelectedCategory: TextView? = null
    private var bottomNavigation: LinearLayout? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private val posts = mutableListOf<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenant_dashboard)

        initViews()
        setupCategoryFilter()
        setupTopIcons()
        setupBottomNavListeners()

        setupRecyclerView()
        loadSamplePosts()
    }

    private fun initViews() {
        btnCategoryFilter = findViewById(R.id.category_filter_container)
        tvSelectedCategory = findViewById(R.id.tv_selected_category)
        bottomNavigation = findViewById(R.id.bottom_navigation)
        recyclerView = findViewById(R.id.recycler_posts)
    }

    private fun setupRecyclerView() {
        postAdapter = PostAdapter(
            posts,
            onHideClick = { post ->
                // Hide logic: remove temporarily from list
                posts.remove(post)
                postAdapter.notifyDataSetChanged()
            },
            onDeleteClick = { post ->
                posts.remove(post)
                postAdapter.notifyDataSetChanged()
            },
            onEmojiClick = { post, emoji ->
                // Optional: handle emoji click, e.g., show a Toast
                // Toast.makeText(this, "${post.author} reacted $emoji", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = postAdapter
    }

    private fun loadSamplePosts() {
        posts.add(
            Post(
                id = "1",
                author = "John Doe",
                category = "Facilities",
                content = "Water leakage in apartment 12B",
                location = "Block A, Floor 1",
                mediaUrl = "https://via.placeholder.com/150"
            )
        )

        posts.add(
            Post(
                id = "2",
                author = "Jane Smith",
                category = "Events",
                content = "Community cleanup this Saturday",
                location = "Central Park",
                mediaUrl = null
            )
        )

        postAdapter.notifyDataSetChanged()
    }

    private fun setupCategoryFilter() {
        btnCategoryFilter?.setOnClickListener {
            showCategoryFilterDialog()
        }
    }

    private fun setupTopIcons() {
        findViewById<ImageView>(R.id.btn_notifications)?.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        findViewById<ImageView>(R.id.btn_quick_actions)?.setOnClickListener {
            startActivity(Intent(this, QuickLinksActivity::class.java))
        }

        findViewById<ImageView>(R.id.btn_profile)?.setOnClickListener {
            startActivity(Intent(this, TenantSettingsActivity::class.java))
        }
    }

    private fun setupBottomNavListeners() {
        val nav = bottomNavigation ?: return

        nav.findViewById<View>(R.id.nav_home)?.setOnClickListener {
            startActivity(Intent(this, QuickLinksActivity::class.java))
        }

        nav.findViewById<View>(R.id.nav_post)?.setOnClickListener {
            startActivity(Intent(this, CreatePostActivity::class.java))
        }

        nav.findViewById<View>(R.id.nav_messages)?.setOnClickListener {
            startActivity(Intent(this, MessagingActivity::class.java))
        }

        nav.findViewById<View>(R.id.nav_dashboard)?.setOnClickListener {
            startActivity(Intent(this, StoreManagementActivity::class.java))
        }

        nav.findViewById<View>(R.id.nav_settings)?.setOnClickListener {
            startActivity(Intent(this, TenantSettingsActivity::class.java))
        }
    }

    private fun showCategoryFilterDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_category_filter)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val categories = listOf(
            R.id.category_all to "All Categories",
            R.id.category_food_dining to "Food & Dining",
            R.id.category_shopping to "Shopping",
            R.id.category_entertainment to "Entertainment",
            R.id.category_facilities to "Facilities",
            R.id.category_events to "Events",
            R.id.category_services to "Services",
            R.id.category_other to "Other"
        )

        categories.forEach { (id, text) ->
            dialog.findViewById<TextView>(id)?.setOnClickListener {
                tvSelectedCategory?.text = text
                dialog.dismiss()
            }
        }

        dialog.show()
    }
}
