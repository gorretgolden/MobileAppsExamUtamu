package com.summitcoaches.app.models

/**
 * User Model - Represents a booking clerk
 * @param id Unique identifier
 * @param fullName Full name of the clerk
 * @param email Email address (used for login)
 * @param phone Phone number
 * @param password Hashed password
 * @param role User role (Clerk/Admin)
 * @param createdAt Registration date
 */
data class User(
    val id: Int = 0,
    val fullName: String,
    val email: String,
    val phone: String,
    val password: String,
    val role: String = "Clerk",
    val createdAt: String = ""
)

/**
 * BusType Model - Represents different types of buses
 * @param id Unique identifier
 * @param typeName Name of bus type (e.g., Luxury, Standard, VIP)
 * @param capacity Total passenger capacity
 * @param description Additional details
 */
data class BusType(
    val id: Int = 0,
    val typeName: String,
    val capacity: Int,
    val description: String = ""
)

/**
 * Bus Model - Represents a physical bus
 * @param id Unique identifier
 * @param busTypeId Foreign key to BusType
 * @param registrationNumber Vehicle registration plate
 * @param model Bus model/make
 * @param status Current status (Active/Maintenance/Inactive)
 */
data class Bus(
    val id: Int = 0,
    val busTypeId: Int,
    val registrationNumber: String,
    val model: String,
    val status: String = "Active"
)

/**
 * Route Model - Represents a travel route
 * @param id Unique identifier
 * @param origin Starting point
 * @param destination End point
 * @param distance Distance in kilometers
 * @param baseFare Passenger fare
 * @param luggageFare Luggage fare
 * @param parcelFare Parcel fare
 */
data class Route(
    val id: Int = 0,
    val origin: String,
    val destination: String,
    val distance: Double,
    val baseFare: Double,
    val luggageFare: Double,
    val parcelFare: Double
)

/**
 * Trip Model - Represents a scheduled bus trip
 * @param id Unique identifier
 * @param busId Foreign key to Bus
 * @param routeId Foreign key to Route
 * @param tripDate Date of the trip
 * @param departureTime Departure time
 * @param arrivalTime Expected arrival time
 * @param availableSeats Current available seats
 * @param status Trip status (Scheduled/In Transit/Completed/Cancelled)
 */
data class Trip(
    val id: Int = 0,
    val busId: Int,
    val routeId: Int,
    val tripDate: String,
    val departureTime: String,
    val arrivalTime: String,
    var availableSeats: Int,
    val status: String = "Scheduled"
)

/**
 * Booking Model - Represents a booking transaction
 * @param id Unique identifier
 * @param bookingReference Unique booking code
 * @param tripId Foreign key to Trip
 * @param clerkId Foreign key to User (booking clerk)
 * @param passengerName Name of passenger/sender
 * @param phoneNumber Contact phone number
 * @param idNumber National ID (optional)
 * @param bookingType Type (Passenger/Luggage/Parcel)
 * @param seatNumber Seat assignment for passengers
 * @param amount Total booking amount
 * @param weight Weight for luggage/parcels
 * @param description Details for parcels
 * @param status Booking status (Confirmed/Cancelled)
 * @param bookingDate Date when booking was made
 */
data class Booking(
    val id: Int = 0,
    val bookingReference: String,
    val tripId: Int,
    val clerkId: Int,
    val passengerName: String,
    val phoneNumber: String,
    val idNumber: String = "",
    val bookingType: String,
    val seatNumber: String = "",
    val amount: Double,
    val weight: Double = 0.0,
    val description: String = "",
    val status: String = "Confirmed",
    val bookingDate: String
)

/**
 * Payment Model - Represents a payment transaction
 * @param id Unique identifier
 * @param bookingId Foreign key to Booking
 * @param amount Payment amount
 * @param paymentMethod Method used (Cash/Mobile Money/Card)
 * @param transactionRef Transaction reference number
 * @param status Payment status (Completed/Pending/Failed)
 * @param paymentDate Date and time of payment
 */
data class Payment(
    val id: Int = 0,
    val bookingId: Int,
    val amount: Double,
    val paymentMethod: String,
    val transactionRef: String,
    val status: String = "Completed",
    val paymentDate: String
)
