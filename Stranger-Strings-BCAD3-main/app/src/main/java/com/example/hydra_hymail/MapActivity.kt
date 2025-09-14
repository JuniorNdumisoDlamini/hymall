package com.example.hydra_hymail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val TAG = "MapActivity"
    private var googleMap: GoogleMap? = null
    private var selectedCategory = "all"

    // example coordinates
    private val sandtonCity = LatLng(-26.1076, 28.0567)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        supportActionBar?.hide()

        // back -> go to ConsumerHomeActivity (as requested)
        findViewById<ImageView?>(R.id.btn_back)?.setOnClickListener {
            startActivity(Intent(this, ConsumerHomeActivity::class.java))
            finish()
        }

        // list view button
        findViewById<TextView?>(R.id.btn_list_view)?.setOnClickListener {
            Toast.makeText(this, "List view coming soon", Toast.LENGTH_SHORT).show()
        }

        // chips wiring
        setupChips()

        // Programmatically add/obtain SupportMapFragment so we avoid xml fragment inflate issues
        try {
            var frag = supportFragmentManager.findFragmentById(R.id.map_container) as? SupportMapFragment
            if (frag == null) {
                frag = SupportMapFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.map_container, frag)
                    .commitNowAllowingStateLoss()
            }
            frag.getMapAsync(this)
        } catch (t: Throwable) {
            Log.e(TAG, "map fragment creation failed: ${t.message}", t)
            Toast.makeText(this, "Unable to initialize map", Toast.LENGTH_LONG).show()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        try {
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(sandtonCity, 16.5f))
            googleMap?.uiSettings?.isZoomControlsEnabled = true
            googleMap?.isIndoorEnabled = true
            addSampleMarkers()
            googleMap?.setOnMarkerClickListener { marker ->
                Toast.makeText(this, "Clicked: ${marker.title}", Toast.LENGTH_SHORT).show()
                true
            }
        } catch (t: Throwable) {
            Log.e(TAG, "Error setting up map: ${t.message}", t)
        }
    }

    private fun addSampleMarkers() {
        val map = googleMap ?: return
        map.addMarker(
            MarkerOptions().position(LatLng(-26.1076, 28.0567))
                .title("Food Court")
                .snippet("Level 1 - Main Floor")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        )
        map.addMarker(
            MarkerOptions().position(LatLng(-26.1078, 28.0569))
                .title("Coffee Shop")
                .snippet("Amazing coffee")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        )
        map.addMarker(
            MarkerOptions().position(LatLng(-26.1074, 28.0565))
                .title("Fashion District")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
        )
        map.addMarker(
            MarkerOptions().position(LatLng(-26.1072, 28.0563))
                .title("Entertainment Zone")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
        map.addMarker(
            MarkerOptions().position(LatLng(-26.1075, 28.0568))
                .title("Sarah's Coffee Post")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        )
    }

    private fun setupChips() {
        val chipIds = listOf(
            R.id.chip_all,
            R.id.chip_food,
            R.id.chip_shopping,
            R.id.chip_facilities,
            R.id.chip_entertainment,
            R.id.chip_services
        )

        chipIds.forEach { id ->
            findViewById<TextView?>(id)?.setOnClickListener { v ->
                val tv = v as? TextView ?: return@setOnClickListener
                selectCategory(tv)
            }
        }
    }

    private fun selectCategory(selectedChip: TextView) {
        selectedCategory = when (selectedChip.id) {
            R.id.chip_food -> "food"
            R.id.chip_shopping -> "shopping"
            R.id.chip_facilities -> "facilities"
            R.id.chip_entertainment -> "entertainment"
            R.id.chip_services -> "services"
            else -> "all"
        }

        val allChips = listOf(
            R.id.chip_all,
            R.id.chip_food,
            R.id.chip_shopping,
            R.id.chip_facilities,
            R.id.chip_entertainment,
            R.id.chip_services
        )

        allChips.forEach { id ->
            val c = findViewById<TextView?>(id)
            c?.setBackgroundResource(R.drawable.button_outline)
            c?.setTextColor(ContextCompat.getColor(this, R.color.text_secondary))
        }

        selectedChip.setBackgroundResource(R.drawable.button_primary)
        selectedChip.setTextColor(ContextCompat.getColor(this, R.color.white))

        Toast.makeText(this, "Filtering: $selectedCategory", Toast.LENGTH_SHORT).show()
        // TODO: filter markers accordingly
    }
}
