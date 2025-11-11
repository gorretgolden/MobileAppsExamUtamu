package com.summitcoaches.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.summitcoaches.app.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val cardCreateBooking: CardView = view.findViewById(R.id.cardCreateBooking)
        val cardBookingHistory: CardView = view.findViewById(R.id.cardBookingHistory)

        cardCreateBooking.setOnClickListener {
            startActivity(Intent(requireContext(), SelectTripActivity::class.java))
        }

        cardBookingHistory.setOnClickListener {
            startActivity(Intent(requireContext(), BookingHistoryActivity::class.java))
        }

        return view
    }
}
