package com.summitcoaches.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.summitcoaches.app.R

data class OnboardingItem(
    val iconRes: Int,
    val title: String,
    val description: String
)

class OnboardingAdapter(private val context: Context) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    private val items = listOf(
        OnboardingItem(
            R.drawable.easy_booking,
            "Easy Booking",
            "Book bus tickets quickly and easily for passengers, luggage, and parcels"
        ),
        OnboardingItem(
            R.drawable.real_time_updates,
            "Real-time Updates",
            "Track your bookings and get live updates on bus schedules"
        ),
        OnboardingItem(
            R.drawable.secure_payment,
            "Secure Payments",
            "Safe and secure payment processing for all your transactions"
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_onboarding, parent, false)
        return OnboardingViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class OnboardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.ivOnboardingIcon)
        private val title: TextView = itemView.findViewById(R.id.tvOnboardingTitle)
        private val description: TextView = itemView.findViewById(R.id.tvOnboardingDesc)

        fun bind(item: OnboardingItem) {
            icon.setImageResource(item.iconRes)
            title.text = item.title
            description.text = item.description
        }
    }
}
