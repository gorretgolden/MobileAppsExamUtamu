package com.summitcoaches.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.summitcoaches.app.R
import com.summitcoaches.app.database.DatabaseHelper
import com.summitcoaches.app.models.Trip

class TripAdapter(
    private val trips: List<Trip>,
    private val onTripClick: (Trip) -> Unit
) : RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trip, parent, false)
        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(trips[position], onTripClick)
    }

    override fun getItemCount() = trips.size

    class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvRoute: TextView = itemView.findViewById(R.id.tvTripRoute)
        private val tvDate: TextView = itemView.findViewById(R.id.tvTripDate)
        private val tvTime: TextView = itemView.findViewById(R.id.tvTripTime)
        private val tvSeats: TextView = itemView.findViewById(R.id.tvTripSeats)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvTripPrice)
        private val btnSelect: Button = itemView.findViewById(R.id.btnSelectTrip)

        fun bind(trip: Trip, onClick: (Trip) -> Unit) {
            val dbHelper = DatabaseHelper(itemView.context)
            val route = dbHelper.getRouteById(trip.routeId)
            
            route?.let {
                tvRoute.text = "${it.origin} → ${it.destination}"
                tvPrice.text = "UGX ${String.format("%.2f", it.baseFare)}"
            }
            
            tvDate.text = trip.tripDate
            tvTime.text = trip.departureTime
            tvSeats.text = "${trip.availableSeats} seats available"
            
            btnSelect.setOnClickListener {
                onClick(trip)
            }
        }
    }
}
