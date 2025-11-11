package com.summitcoaches.app.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.summitcoaches.app.R
import com.summitcoaches.app.adapters.BookingAdapter
import com.summitcoaches.app.database.DatabaseHelper
import com.summitcoaches.app.models.Booking
import com.summitcoaches.app.utils.SessionManager

class BookingHistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookingAdapter: BookingAdapter
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager
    private var bookingsList = mutableListOf<Booking>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_history)

        supportActionBar?.title = "Booking History"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = DatabaseHelper(this)
        sessionManager = SessionManager(this)
        recyclerView = findViewById(R.id.recyclerViewBookings)

        setupRecyclerView()
        loadBookings()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        bookingAdapter = BookingAdapter(bookingsList, dbHelper)
        recyclerView.adapter = bookingAdapter
    }

    private fun loadBookings() {
        bookingsList.clear()
        bookingsList.addAll(dbHelper.getBookingsByClerk(sessionManager.getUserId()))
        
        if (bookingsList.isEmpty()) {
            Toast.makeText(this, "No bookings found", Toast.LENGTH_SHORT).show()
        }
        
        bookingAdapter.notifyDataSetChanged()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
