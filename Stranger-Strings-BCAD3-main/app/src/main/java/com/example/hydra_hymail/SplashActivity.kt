package com.example.hydra_hymail

import android.animation.ObjectAnimator
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SplashActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var dot1: View
    private lateinit var dot2: View
    private lateinit var dot3: View

    private var mediaPlayer: MediaPlayer? = null
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Hide status bar for full screen experience
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        initViews()
        startLoadingAnimation()
    }

    private fun initViews() {
        progressBar = findViewById(R.id.progressBar)
        dot1 = findViewById(R.id.dot1)
        dot2 = findViewById(R.id.dot2)
        dot3 = findViewById(R.id.dot3)
    }

    private fun startLoadingAnimation() {
        // Initialize progress bar
        progressBar.progress = 0

        // Start progress bar animation
        val progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
        progressAnimator.duration = 3000 // 3 seconds
        progressAnimator.start()

        // Start loading dots animation
        animateLoadingDots()

        // Play loading sound
        playLoadingSound()

        // Add progress listener for sound effects
        progressAnimator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Int
            when (progress) {
                25, 50, 75 -> playProgressSound()
                100 -> {
                    playCompletionSound()
                    handler.postDelayed({
                        navigateToMainActivity()
                    }, 500)
                }
            }
        }
    }

    private fun animateLoadingDots() {
        val dots = arrayOf(dot1, dot2, dot3)
        var currentDot = 0

        val dotAnimationRunnable = object : Runnable {
            override fun run() {
                // Reset all dots to inactive
                dots.forEach { dot ->
                    dot.background = ContextCompat.getDrawable(
                        this@SplashActivity,
                        R.drawable.loading_dot
                    )
                }
                // Activate current dot
                dots[currentDot].background = ContextCompat.getDrawable(
                    this@SplashActivity,
                    R.drawable.loading_dot_active
                )
                // Move to next dot
                currentDot = (currentDot + 1) % dots.size
                // Schedule next animation
                handler.postDelayed(this, 500)
            }
        }
        handler.post(dotAnimationRunnable)
    }

    private fun playLoadingSound() {
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.notification_sound_1)
            mediaPlayer?.apply {
                isLooping = false
                setVolume(0.3f, 0.3f)
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun playProgressSound() {
        try {
            val progressPlayer = MediaPlayer.create(this, R.raw.notification_sound_1)
            progressPlayer?.apply {
                setVolume(0.2f, 0.2f)
                start()
                setOnCompletionListener { mp -> mp.release() }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun playCompletionSound() {
        try {
            val completionPlayer = MediaPlayer.create(this, R.raw.notification_sound_1)
            completionPlayer?.apply {
                setVolume(0.4f, 0.4f)
                start()
                setOnCompletionListener { mp -> mp.release() }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, RolesActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        handler.removeCallbacksAndMessages(null)
    }
}
