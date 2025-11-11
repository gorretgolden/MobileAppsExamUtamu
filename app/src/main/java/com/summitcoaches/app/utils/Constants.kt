package com.summitcoaches.app.utils

/**
 * Constants - Application-wide constant values
 */
object Constants {
    // SharedPreferences keys
    const val PREF_NAME = "SummitCoachesPrefs"
    const val KEY_USER_ID = "user_id"
    const val KEY_USER_NAME = "user_name"
    const val KEY_USER_EMAIL = "user_email"
    const val KEY_USER_ROLE = "user_role"
    const val KEY_IS_LOGGED_IN = "is_logged_in"
    const val KEY_FIRST_TIME = "first_time"

    // Booking types
    const val BOOKING_TYPE_PASSENGER = "Passenger"
    const val BOOKING_TYPE_LUGGAGE = "Luggage"
    const val BOOKING_TYPE_PARCEL = "Parcel"

    // Booking status
    const val STATUS_CONFIRMED = "Confirmed"
    const val STATUS_CANCELLED = "Cancelled"
    const val STATUS_PENDING = "Pending"

    // Payment methods
    const val PAYMENT_CASH = "Cash"
    const val PAYMENT_MOBILE_MONEY = "Mobile Money"
    const val PAYMENT_CARD = "Card"

    // Trip status
    const val TRIP_SCHEDULED = "Scheduled"
    const val TRIP_IN_TRANSIT = "In Transit"
    const val TRIP_COMPLETED = "Completed"
    const val TRIP_CANCELLED = "Cancelled"

    // Date formats
    const val DATE_FORMAT = "yyyy-MM-dd"
    const val TIME_FORMAT = "HH:mm"
    const val DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

    // Validation
    const val MIN_PASSWORD_LENGTH = 6
    const val PHONE_REGEX = "^[0-9]{10,13}$"
    const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
}
