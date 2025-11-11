package com.summitcoaches.app.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.summitcoaches.app.models.*
import java.security.MessageDigest

/**
 * DatabaseHelper - Main database manager for Summit Coaches
 * Handles all database operations including CRUD for all tables
 * Uses SQLite database for local data storage
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DBConstants.DATABASE_NAME,
    null,
    DBConstants.DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        // Create all tables
        db.execSQL(DBConstants.CREATE_TABLE_USERS)
        db.execSQL(DBConstants.CREATE_TABLE_BUS_TYPES)
        db.execSQL(DBConstants.CREATE_TABLE_BUSES)
        db.execSQL(DBConstants.CREATE_TABLE_ROUTES)
        db.execSQL(DBConstants.CREATE_TABLE_TRIPS)
        db.execSQL(DBConstants.CREATE_TABLE_BOOKINGS)
        db.execSQL(DBConstants.CREATE_TABLE_PAYMENTS)

        // Insert dummy data
        insertDummyData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop all tables and recreate
        db.execSQL("DROP TABLE IF EXISTS ${DBConstants.TABLE_PAYMENTS}")
        db.execSQL("DROP TABLE IF EXISTS ${DBConstants.TABLE_BOOKINGS}")
        db.execSQL("DROP TABLE IF EXISTS ${DBConstants.TABLE_TRIPS}")
        db.execSQL("DROP TABLE IF EXISTS ${DBConstants.TABLE_ROUTES}")
        db.execSQL("DROP TABLE IF EXISTS ${DBConstants.TABLE_BUSES}")
        db.execSQL("DROP TABLE IF EXISTS ${DBConstants.TABLE_BUS_TYPES}")
        db.execSQL("DROP TABLE IF EXISTS ${DBConstants.TABLE_USERS}")
        onCreate(db)
    }

    /**
     * Insert dummy data for testing
     */
    private fun insertDummyData(db: SQLiteDatabase) {
        // Insert default admin user
        val adminValues = ContentValues().apply {
            put(DBConstants.COL_USER_FULL_NAME, "Admin User")
            put(DBConstants.COL_USER_EMAIL, "admin@summitcoaches.com")
            put(DBConstants.COL_USER_PHONE, "0700000000")
            put(DBConstants.COL_USER_PASSWORD, hashPassword("admin123"))
            put(DBConstants.COL_USER_ROLE, "Admin")
        }
        db.insert(DBConstants.TABLE_USERS, null, adminValues)

        // Insert sample clerk
        val clerkValues = ContentValues().apply {
            put(DBConstants.COL_USER_FULL_NAME, "John Doe")
            put(DBConstants.COL_USER_EMAIL, "clerk@summitcoaches.com")
            put(DBConstants.COL_USER_PHONE, "0700000001")
            put(DBConstants.COL_USER_PASSWORD, hashPassword("clerk123"))
            put(DBConstants.COL_USER_ROLE, "Clerk")
        }
        db.insert(DBConstants.TABLE_USERS, null, clerkValues)

        // Insert bus types
        insertBusType(db, "Standard", 45, "Regular seating bus")
        insertBusType(db, "Luxury", 32, "Premium comfort with reclining seats")
        insertBusType(db, "VIP", 24, "Executive class with extra legroom")

        // Insert buses
        insertBus(db, 1, "UAH 123A", "Yutong ZK6129H")
        insertBus(db, 2, "UAH 456B", "Scania Touring")
        insertBus(db, 1, "UAH 789C", "Mercedes-Benz O500R")
        insertBus(db, 3, "UAH 012D", "Volvo 9700")

        // Insert routes
        insertRoute(db, "Kampala", "Mbarara", 270.0, 45000.0, 15000.0, 20000.0)
        insertRoute(db, "Kampala", "Gulu", 340.0, 55000.0, 18000.0, 25000.0)
        insertRoute(db, "Kampala", "Mbale", 245.0, 40000.0, 12000.0, 18000.0)
        insertRoute(db, "Kampala", "Fort Portal", 320.0, 50000.0, 16000.0, 22000.0)
        insertRoute(db, "Mbarara", "Kabale", 150.0, 30000.0, 10000.0, 15000.0)

        // Insert trips for today and tomorrow
        val today = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            .format(java.util.Date())
        val tomorrow = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            .format(java.util.Date(System.currentTimeMillis() + 86400000))

        insertTrip(db, 1, 1, today, "08:00", "13:00", 45)
        insertTrip(db, 2, 2, today, "10:00", "16:00", 32)
        insertTrip(db, 3, 3, today, "14:00", "19:00", 45)
        insertTrip(db, 4, 4, tomorrow, "07:00", "13:00", 24)
        insertTrip(db, 1, 1, tomorrow, "15:00", "20:00", 45)
    }

    private fun insertBusType(db: SQLiteDatabase, name: String, capacity: Int, desc: String) {
        val values = ContentValues().apply {
            put(DBConstants.COL_BUS_TYPE_NAME, name)
            put(DBConstants.COL_BUS_TYPE_CAPACITY, capacity)
            put(DBConstants.COL_BUS_TYPE_DESC, desc)
        }
        db.insert(DBConstants.TABLE_BUS_TYPES, null, values)
    }

    private fun insertBus(db: SQLiteDatabase, typeId: Int, regNumber: String, model: String) {
        val values = ContentValues().apply {
            put(DBConstants.COL_BUS_TYPE_ID, typeId)
            put(DBConstants.COL_BUS_REG_NUMBER, regNumber)
            put(DBConstants.COL_BUS_MODEL, model)
            put(DBConstants.COL_BUS_STATUS, "Active")
        }
        db.insert(DBConstants.TABLE_BUSES, null, values)
    }

    private fun insertRoute(db: SQLiteDatabase, origin: String, destination: String,
                           distance: Double, baseFare: Double, luggageFare: Double, parcelFare: Double) {
        val values = ContentValues().apply {
            put(DBConstants.COL_ROUTE_ORIGIN, origin)
            put(DBConstants.COL_ROUTE_DESTINATION, destination)
            put(DBConstants.COL_ROUTE_DISTANCE, distance)
            put(DBConstants.COL_ROUTE_BASE_FARE, baseFare)
            put(DBConstants.COL_ROUTE_LUGGAGE_FARE, luggageFare)
            put(DBConstants.COL_ROUTE_PARCEL_FARE, parcelFare)
        }
        db.insert(DBConstants.TABLE_ROUTES, null, values)
    }

    private fun insertTrip(db: SQLiteDatabase, busId: Int, routeId: Int, date: String,
                          departure: String, arrival: String, seats: Int) {
        val values = ContentValues().apply {
            put(DBConstants.COL_TRIP_BUS_ID, busId)
            put(DBConstants.COL_TRIP_ROUTE_ID, routeId)
            put(DBConstants.COL_TRIP_DATE, date)
            put(DBConstants.COL_TRIP_DEPARTURE_TIME, departure)
            put(DBConstants.COL_TRIP_ARRIVAL_TIME, arrival)
            put(DBConstants.COL_TRIP_AVAILABLE_SEATS, seats)
            put(DBConstants.COL_TRIP_STATUS, "Scheduled")
        }
        db.insert(DBConstants.TABLE_TRIPS, null, values)
    }

    /**
     * Hash password using SHA-256
     */
    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    // ==================== USER OPERATIONS ====================

    /**
     * Create new user (register)
     */
    fun createUser(user: User): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DBConstants.COL_USER_FULL_NAME, user.fullName)
            put(DBConstants.COL_USER_EMAIL, user.email)
            put(DBConstants.COL_USER_PHONE, user.phone)
            put(DBConstants.COL_USER_PASSWORD, hashPassword(user.password))
            put(DBConstants.COL_USER_ROLE, user.role)
        }
        return db.insert(DBConstants.TABLE_USERS, null, values)
    }

    /**
     * Login user - verify credentials
     */
    fun loginUser(email: String, password: String): User? {
        val db = readableDatabase
        val hashedPassword = hashPassword(password)
        val cursor = db.query(
            DBConstants.TABLE_USERS,
            null,
            "${DBConstants.COL_USER_EMAIL} = ? AND ${DBConstants.COL_USER_PASSWORD} = ?",
            arrayOf(email, hashedPassword),
            null, null, null
        )

        return if (cursor.moveToFirst()) {
            val user = cursorToUser(cursor)
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    /**
     * Get user by ID
     */
    fun getUserById(id: Int): User? {
        val db = readableDatabase
        val cursor = db.query(
            DBConstants.TABLE_USERS,
            null,
            "${DBConstants.COL_ID} = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        return if (cursor.moveToFirst()) {
            val user = cursorToUser(cursor)
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    /**
     * Update user details
     */
    fun updateUser(user: User): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DBConstants.COL_USER_FULL_NAME, user.fullName)
            put(DBConstants.COL_USER_EMAIL, user.email)
            put(DBConstants.COL_USER_PHONE, user.phone)
        }
        return db.update(
            DBConstants.TABLE_USERS,
            values,
            "${DBConstants.COL_ID} = ?",
            arrayOf(user.id.toString())
        )
    }

    /**
     * Check if email exists
     */
    fun emailExists(email: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            DBConstants.TABLE_USERS,
            arrayOf(DBConstants.COL_ID),
            "${DBConstants.COL_USER_EMAIL} = ?",
            arrayOf(email),
            null, null, null
        )
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    private fun cursorToUser(cursor: Cursor): User {
        return User(
            id = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COL_ID)),
            fullName = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_USER_FULL_NAME)),
            email = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_USER_EMAIL)),
            phone = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_USER_PHONE)),
            password = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_USER_PASSWORD)),
            role = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_USER_ROLE)),
            createdAt = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_CREATED_AT))
        )
    }

    // ==================== TRIP OPERATIONS ====================

    /**
     * Get all available trips
     */
    fun getAllTrips(): List<Trip> {
        val trips = mutableListOf<Trip>()
        val db = readableDatabase
        val cursor = db.query(
            DBConstants.TABLE_TRIPS,
            null,
            "${DBConstants.COL_TRIP_STATUS} = ?",
            arrayOf("Scheduled"),
            null, null,
            "${DBConstants.COL_TRIP_DATE} ASC, ${DBConstants.COL_TRIP_DEPARTURE_TIME} ASC"
        )

        while (cursor.moveToNext()) {
            trips.add(cursorToTrip(cursor))
        }
        cursor.close()
        return trips
    }

    /**
     * Get trip by ID
     */
    fun getTripById(id: Int): Trip? {
        val db = readableDatabase
        val cursor = db.query(
            DBConstants.TABLE_TRIPS,
            null,
            "${DBConstants.COL_ID} = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        return if (cursor.moveToFirst()) {
            val trip = cursorToTrip(cursor)
            cursor.close()
            trip
        } else {
            cursor.close()
            null
        }
    }

    /**
     * Update trip
     */
    fun updateTrip(trip: Trip): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DBConstants.COL_TRIP_AVAILABLE_SEATS, trip.availableSeats)
            put(DBConstants.COL_TRIP_STATUS, trip.status)
        }
        return db.update(
            DBConstants.TABLE_TRIPS,
            values,
            "${DBConstants.COL_ID} = ?",
            arrayOf(trip.id.toString())
        )
    }

    private fun cursorToTrip(cursor: Cursor): Trip {
        return Trip(
            id = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COL_ID)),
            busId = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COL_TRIP_BUS_ID)),
            routeId = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COL_TRIP_ROUTE_ID)),
            tripDate = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_TRIP_DATE)),
            departureTime = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_TRIP_DEPARTURE_TIME)),
            arrivalTime = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_TRIP_ARRIVAL_TIME)),
            availableSeats = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COL_TRIP_AVAILABLE_SEATS)),
            status = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_TRIP_STATUS))
        )
    }

    // ==================== ROUTE OPERATIONS ====================

    /**
     * Get route by ID
     */
    fun getRouteById(id: Int): Route? {
        val db = readableDatabase
        val cursor = db.query(
            DBConstants.TABLE_ROUTES,
            null,
            "${DBConstants.COL_ID} = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        return if (cursor.moveToFirst()) {
            val route = cursorToRoute(cursor)
            cursor.close()
            route
        } else {
            cursor.close()
            null
        }
    }

    private fun cursorToRoute(cursor: Cursor): Route {
        return Route(
            id = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COL_ID)),
            origin = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_ROUTE_ORIGIN)),
            destination = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_ROUTE_DESTINATION)),
            distance = cursor.getDouble(cursor.getColumnIndexOrThrow(DBConstants.COL_ROUTE_DISTANCE)),
            baseFare = cursor.getDouble(cursor.getColumnIndexOrThrow(DBConstants.COL_ROUTE_BASE_FARE)),
            luggageFare = cursor.getDouble(cursor.getColumnIndexOrThrow(DBConstants.COL_ROUTE_LUGGAGE_FARE)),
            parcelFare = cursor.getDouble(cursor.getColumnIndexOrThrow(DBConstants.COL_ROUTE_PARCEL_FARE))
        )
    }

    // ==================== BUS OPERATIONS ====================

    /**
     * Get bus by ID
     */
    fun getBusById(id: Int): Bus? {
        val db = readableDatabase
        val cursor = db.query(
            DBConstants.TABLE_BUSES,
            null,
            "${DBConstants.COL_ID} = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        return if (cursor.moveToFirst()) {
            val bus = cursorToBus(cursor)
            cursor.close()
            bus
        } else {
            cursor.close()
            null
        }
    }

    private fun cursorToBus(cursor: Cursor): Bus {
        return Bus(
            id = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COL_ID)),
            busTypeId = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COL_BUS_TYPE_ID)),
            registrationNumber = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_BUS_REG_NUMBER)),
            model = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_BUS_MODEL)),
            status = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_BUS_STATUS))
        )
    }

    // ==================== BOOKING OPERATIONS ====================

    /**
     * Create new booking
     */
    fun createBooking(booking: Booking): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DBConstants.COL_BOOKING_REF, booking.bookingReference)
            put(DBConstants.COL_BOOKING_TRIP_ID, booking.tripId)
            put(DBConstants.COL_BOOKING_CLERK_ID, booking.clerkId)
            put(DBConstants.COL_BOOKING_PASSENGER_NAME, booking.passengerName)
            put(DBConstants.COL_BOOKING_PHONE, booking.phoneNumber)
            put(DBConstants.COL_BOOKING_ID_NUMBER, booking.idNumber)
            put(DBConstants.COL_BOOKING_TYPE, booking.bookingType)
            put(DBConstants.COL_BOOKING_SEAT_NUMBER, booking.seatNumber)
            put(DBConstants.COL_BOOKING_AMOUNT, booking.amount)
            put(DBConstants.COL_BOOKING_WEIGHT, booking.weight)
            put(DBConstants.COL_BOOKING_DESC, booking.description)
            put(DBConstants.COL_BOOKING_STATUS, booking.status)
            put(DBConstants.COL_BOOKING_DATE, booking.bookingDate)
        }
        return db.insert(DBConstants.TABLE_BOOKINGS, null, values)
    }

    /**
     * Get bookings by clerk ID
     */
    fun getBookingsByClerk(clerkId: Int): List<Booking> {
        val bookings = mutableListOf<Booking>()
        val db = readableDatabase
        val cursor = db.query(
            DBConstants.TABLE_BOOKINGS,
            null,
            "${DBConstants.COL_BOOKING_CLERK_ID} = ?",
            arrayOf(clerkId.toString()),
            null, null,
            "${DBConstants.COL_BOOKING_DATE} DESC"
        )

        while (cursor.moveToNext()) {
            bookings.add(cursorToBooking(cursor))
        }
        cursor.close()
        return bookings
    }

    /**
     * Get all bookings
     */
    fun getAllBookings(): List<Booking> {
        val bookings = mutableListOf<Booking>()
        val db = readableDatabase
        val cursor = db.query(
            DBConstants.TABLE_BOOKINGS,
            null, null, null, null, null,
            "${DBConstants.COL_BOOKING_DATE} DESC"
        )

        while (cursor.moveToNext()) {
            bookings.add(cursorToBooking(cursor))
        }
        cursor.close()
        return bookings
    }

    private fun cursorToBooking(cursor: Cursor): Booking {
        return Booking(
            id = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COL_ID)),
            bookingReference = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_BOOKING_REF)),
            tripId = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COL_BOOKING_TRIP_ID)),
            clerkId = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COL_BOOKING_CLERK_ID)),
            passengerName = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_BOOKING_PASSENGER_NAME)),
            phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_BOOKING_PHONE)),
            idNumber = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_BOOKING_ID_NUMBER)),
            bookingType = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_BOOKING_TYPE)),
            seatNumber = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_BOOKING_SEAT_NUMBER)),
            amount = cursor.getDouble(cursor.getColumnIndexOrThrow(DBConstants.COL_BOOKING_AMOUNT)),
            weight = cursor.getDouble(cursor.getColumnIndexOrThrow(DBConstants.COL_BOOKING_WEIGHT)),
            description = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_BOOKING_DESC)),
            status = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_BOOKING_STATUS)),
            bookingDate = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_BOOKING_DATE))
        )
    }

    // ==================== PAYMENT OPERATIONS ====================

    /**
     * Create payment record
     */
    fun createPayment(payment: Payment): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DBConstants.COL_PAYMENT_BOOKING_ID, payment.bookingId)
            put(DBConstants.COL_PAYMENT_AMOUNT, payment.amount)
            put(DBConstants.COL_PAYMENT_METHOD, payment.paymentMethod)
            put(DBConstants.COL_PAYMENT_TRANSACTION_REF, payment.transactionRef)
            put(DBConstants.COL_PAYMENT_STATUS, payment.status)
            put(DBConstants.COL_PAYMENT_DATE, payment.paymentDate)
        }
        return db.insert(DBConstants.TABLE_PAYMENTS, null, values)
    }
}
