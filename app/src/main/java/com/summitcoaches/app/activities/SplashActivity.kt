package com.summitcoaches.app.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.summitcoaches.app.R
import com.summitcoaches.app.utils.SessionManager

/**
 * SplashActivity - App entry point
 * Shows splash screen for 2 seconds then navigates to appropriate screen
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private val splashDuration = 2000L // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sessionManager = SessionManager(this)

        // Navigate after splash duration
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToNextScreen()
        }, splashDuration)
    }

    /**
     * Navigate to appropriate screen based on user state
     */
    private fun navigateToNextScreen() {
        val intent = when {
            // First time launch - show onboarding
            sessionManager.isFirstTimeLaunch() -> {
                sessionManager.setFirstTimeLaunch(false)
                Intent(this, OnboardingActivity::class.java)
            }
            // User already logged in - go to dashboard
            sessionManager.isLoggedIn() -> {
                Intent(this, DashboardActivity::class.java)
            }
            // Not logged in - show login
            else -> {
                Intent(this, LoginActivity::class.java)
            }
        }

        startActivity(intent)
        finish()
    }
}
