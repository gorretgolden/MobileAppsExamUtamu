package com.summitcoaches.app.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.summitcoaches.app.R
import com.summitcoaches.app.utils.SessionManager

class DashboardActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var cardCreateBooking: CardView
    private lateinit var cardBookingHistory: CardView
    private lateinit var cardAccountDetails: CardView
    private lateinit var cardLogout: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        sessionManager = SessionManager(this)

        // Check if user is logged in
        if (!sessionManager.isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        cardCreateBooking = findViewById(R.id.cardCreateBooking)
        cardBookingHistory = findViewById(R.id.cardBookingHistory)
        cardAccountDetails = findViewById(R.id.cardAccountDetails)
        cardLogout = findViewById(R.id.cardLogout)

        supportActionBar?.title = "Welcome, ${sessionManager.getUserName()}"
    }

    private fun setupListeners() {
        cardCreateBooking.setOnClickListener {
            startActivity(Intent(this, SelectTripActivity::class.java))
        }

        cardBookingHistory.setOnClickListener {
            startActivity(Intent(this, BookingHistoryActivity::class.java))
        }

        cardAccountDetails.setOnClickListener {
            startActivity(Intent(this, AccountDetailsActivity::class.java))
        }

        cardLogout.setOnClickListener {
            sessionManager.clearSession()
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
