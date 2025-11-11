package com.summitcoaches.app.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.summitcoaches.app.R
import com.summitcoaches.app.adapters.TripAdapter
import com.summitcoaches.app.database.DatabaseHelper
import com.summitcoaches.app.models.Trip

class SelectTripActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tripAdapter: TripAdapter
    private lateinit var dbHelper: DatabaseHelper
    private var tripsList = mutableListOf<Trip>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_trip)

        supportActionBar?.title = "Select Trip"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerViewTrips)

        setupRecyclerView()
        loadTrips()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        tripAdapter = TripAdapter(tripsList) { trip ->
            onTripSelected(trip)
        }
        recyclerView.adapter = tripAdapter
    }

    private fun loadTrips() {
        tripsList.clear()
        tripsList.addAll(dbHelper.getAllTrips())
        
        if (tripsList.isEmpty()) {
            Toast.makeText(this, "No trips available", Toast.LENGTH_SHORT).show()
        }
        
        tripAdapter.notifyDataSetChanged()
    }

    private fun onTripSelected(trip: Trip) {
        val intent = Intent(this, CreateBookingActivity::class.java)
        intent.putExtra("TRIP_ID", trip.id)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
