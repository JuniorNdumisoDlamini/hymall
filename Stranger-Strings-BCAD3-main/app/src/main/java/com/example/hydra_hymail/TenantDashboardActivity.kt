package com.example.hydra_hymail

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TenantDashboardActivity : AppCompatActivity() {

    private var btnCategoryFilter: View? = null
    private var tvSelectedCategory: TextView? = null
    private var bottomNavigation: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenant_dashboard)

        initViews()
        setupCategoryFilter()
        setupTopIcons()
        setupBottomNavListeners()
        setupPostClicksIfPresent()
    }

    private fun initViews() {
        // Use typed findViewById to avoid ambiguity
        btnCategoryFilter = findViewById<View>(R.id.category_filter_container)
        tvSelectedCategory = findViewById<TextView>(R.id.tv_selected_category)
        bottomNavigation = findViewById<LinearLayout>(R.id.bottom_navigation)
    }

    private fun setupCategoryFilter() {
        btnCategoryFilter?.setOnClickListener {
            showCategoryFilterDialog()
        }
    }

    private fun setupTopIcons() {
        findViewById<ImageView?>(R.id.btn_notifications)?.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        findViewById<ImageView?>(R.id.btn_quick_actions)?.setOnClickListener {
            startActivity(Intent(this, QuickLinksActivity::class.java))
        }

        findViewById<ImageView?>(R.id.btn_profile)?.setOnClickListener {
            startActivity(Intent(this, TenantSettingsActivity::class.java))
        }
    }

    private fun setupBottomNavListeners() {
        val nav = bottomNavigation ?: return

        nav.findViewById<View?>(R.id.nav_home)?.setOnClickListener {
            startActivity(Intent(this, QuickLinksActivity::class.java))
        }

        nav.findViewById<View?>(R.id.nav_post)?.setOnClickListener {
            startActivity(Intent(this, CreatePostActivity::class.java))
        }

        nav.findViewById<View?>(R.id.nav_messages)?.setOnClickListener {
            startActivity(Intent(this, MessagingActivity::class.java))
        }

        nav.findViewById<View?>(R.id.nav_dashboard)?.setOnClickListener {
            startActivity(Intent(this, StoreManagementActivity::class.java))
        }

        nav.findViewById<View?>(R.id.nav_settings)?.setOnClickListener {
            startActivity(Intent(this, TenantSettingsActivity::class.java))
        }
    }

    private fun setupPostClicksIfPresent() {
        // These ids must exist in the layout (I suggested adding them above)
        findViewById<View?>(R.id.post_container_1)?.setOnClickListener {
            startActivity(Intent(this, PostDetailActivity::class.java))
        }

        findViewById<View?>(R.id.post_container_2)?.setOnClickListener {
            startActivity(Intent(this, PostDetailActivity::class.java))
        }
    }

    private fun showCategoryFilterDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_category_filter)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.findViewById<TextView?>(R.id.category_all)?.setOnClickListener {
            tvSelectedCategory?.text = "All Categories"
            dialog.dismiss()
        }

        dialog.findViewById<TextView?>(R.id.category_food_dining)?.setOnClickListener {
            tvSelectedCategory?.text = "Food & Dining"
            dialog.dismiss()
        }

        dialog.findViewById<TextView?>(R.id.category_shopping)?.setOnClickListener {
            tvSelectedCategory?.text = "Shopping"
            dialog.dismiss()
        }

        dialog.findViewById<TextView?>(R.id.category_entertainment)?.setOnClickListener {
            tvSelectedCategory?.text = "Entertainment"
            dialog.dismiss()
        }

        dialog.findViewById<TextView?>(R.id.category_facilities)?.setOnClickListener {
            tvSelectedCategory?.text = "Facilities"
            dialog.dismiss()
        }

        dialog.findViewById<TextView?>(R.id.category_events)?.setOnClickListener {
            tvSelectedCategory?.text = "Events"
            dialog.dismiss()
        }

        dialog.findViewById<TextView?>(R.id.category_services)?.setOnClickListener {
            tvSelectedCategory?.text = "Services"
            dialog.dismiss()
        }

        dialog.findViewById<TextView?>(R.id.category_other)?.setOnClickListener {
            tvSelectedCategory?.text = "Other"
            dialog.dismiss()
        }

        dialog.show()
    }
}
