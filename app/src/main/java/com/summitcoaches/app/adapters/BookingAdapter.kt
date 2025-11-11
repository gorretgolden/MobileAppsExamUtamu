package com.summitcoaches.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.summitcoaches.app.R
import com.summitcoaches.app.database.DatabaseHelper
import com.summitcoaches.app.models.Booking

class BookingAdapter(
    private val bookings: List<Booking>,
    private val dbHelper: DatabaseHelper
) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_booking, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        holder.bind(bookings[position], dbHelper)
    }

    override fun getItemCount() = bookings.size

    class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvBookingRef: TextView = itemView.findViewById(R.id.tvBookingRef)
        private val tvRoute: TextView = itemView.findViewById(R.id.tvBookingRoute)
        private val tvPassenger: TextView = itemView.findViewById(R.id.tvPassengerName)
        private val tvDate: TextView = itemView.findViewById(R.id.tvBookingDate)
        private val tvAmount: TextView = itemView.findViewById(R.id.tvBookingAmount)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvBookingStatus)
        private val tvType: TextView = itemView.findViewById(R.id.tvBookingType)

        fun bind(booking: Booking, dbHelper: DatabaseHelper) {
            val trip = dbHelper.getTripById(booking.tripId)
            val route = trip?.let { dbHelper.getRouteById(it.routeId) }
            
            tvBookingRef.text = booking.bookingReference
            tvRoute.text = if (route != null) "${route.origin} → ${route.destination}" else "N/A"
            tvPassenger.text = booking.passengerName
            tvDate.text = "${trip?.tripDate} ${trip?.departureTime}"
            tvAmount.text = "UGX ${String.format("%.2f", booking.amount)}"
            tvStatus.text = booking.status
            tvType.text = booking.bookingType
        }
    }
}
