package com.summitcoaches.app.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.summitcoaches.app.R
import android.widget.TextView

class AboutActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Display app info
        findViewById<TextView>(R.id.tvAbout).text = "Summit Coaches v1\nBook buses easily for passengers, luggage, and parcels."
    }
}
