package com.summitcoaches.app.database

/**
 * DBConstants - Database schema constants
 * Contains table names, column names, and SQL statements
 */
object DBConstants {
    // Database info
    const val DATABASE_NAME = "summit_coaches.db"
    const val DATABASE_VERSION = 1

    // Table names
    const val TABLE_USERS = "users"
    const val TABLE_BUS_TYPES = "bus_types"
    const val TABLE_BUSES = "buses"
    const val TABLE_ROUTES = "routes"
    const val TABLE_TRIPS = "trips"
    const val TABLE_BOOKINGS = "bookings"
    const val TABLE_PAYMENTS = "payments"

    // Common columns
    const val COL_ID = "id"
    const val COL_CREATED_AT = "created_at"

    // Users table columns
    const val COL_USER_FULL_NAME = "full_name"
    const val COL_USER_EMAIL = "email"
    const val COL_USER_PHONE = "phone"
    const val COL_USER_PASSWORD = "password"
    const val COL_USER_ROLE = "role"

    // Bus Types table columns
    const val COL_BUS_TYPE_NAME = "type_name"
    const val COL_BUS_TYPE_CAPACITY = "capacity"
    const val COL_BUS_TYPE_DESC = "description"

    // Buses table columns
    const val COL_BUS_TYPE_ID = "bus_type_id"
    const val COL_BUS_REG_NUMBER = "registration_number"
    const val COL_BUS_MODEL = "model"
    const val COL_BUS_STATUS = "status"

    // Routes table columns
    const val COL_ROUTE_ORIGIN = "origin"
    const val COL_ROUTE_DESTINATION = "destination"
    const val COL_ROUTE_DISTANCE = "distance"
    const val COL_ROUTE_BASE_FARE = "base_fare"
    const val COL_ROUTE_LUGGAGE_FARE = "luggage_fare"
    const val COL_ROUTE_PARCEL_FARE = "parcel_fare"

    // Trips table columns
    const val COL_TRIP_BUS_ID = "bus_id"
    const val COL_TRIP_ROUTE_ID = "route_id"
    const val COL_TRIP_DATE = "trip_date"
    const val COL_TRIP_DEPARTURE_TIME = "departure_time"
    const val COL_TRIP_ARRIVAL_TIME = "arrival_time"
    const val COL_TRIP_AVAILABLE_SEATS = "available_seats"
    const val COL_TRIP_STATUS = "status"

    // Bookings table columns
    const val COL_BOOKING_REF = "booking_reference"
    const val COL_BOOKING_TRIP_ID = "trip_id"
    const val COL_BOOKING_CLERK_ID = "clerk_id"
    const val COL_BOOKING_PASSENGER_NAME = "passenger_name"
    const val COL_BOOKING_PHONE = "phone_number"
    const val COL_BOOKING_ID_NUMBER = "id_number"
    const val COL_BOOKING_TYPE = "booking_type"
    const val COL_BOOKING_SEAT_NUMBER = "seat_number"
    const val COL_BOOKING_AMOUNT = "amount"
    const val COL_BOOKING_WEIGHT = "weight"
    const val COL_BOOKING_DESC = "description"
    const val COL_BOOKING_STATUS = "status"
    const val COL_BOOKING_DATE = "booking_date"

    // Payments table columns
    const val COL_PAYMENT_BOOKING_ID = "booking_id"
    const val COL_PAYMENT_AMOUNT = "amount"
    const val COL_PAYMENT_METHOD = "payment_method"
    const val COL_PAYMENT_TRANSACTION_REF = "transaction_ref"
    const val COL_PAYMENT_STATUS = "status"
    const val COL_PAYMENT_DATE = "payment_date"

    // Create table statements
    const val CREATE_TABLE_USERS = """
        CREATE TABLE $TABLE_USERS (
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_USER_FULL_NAME TEXT NOT NULL,
            $COL_USER_EMAIL TEXT UNIQUE NOT NULL,
            $COL_USER_PHONE TEXT NOT NULL,
            $COL_USER_PASSWORD TEXT NOT NULL,
            $COL_USER_ROLE TEXT DEFAULT 'Clerk',
            $COL_CREATED_AT DATETIME DEFAULT CURRENT_TIMESTAMP
        )
    """

    const val CREATE_TABLE_BUS_TYPES = """
        CREATE TABLE $TABLE_BUS_TYPES (
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_BUS_TYPE_NAME TEXT NOT NULL,
            $COL_BUS_TYPE_CAPACITY INTEGER NOT NULL,
            $COL_BUS_TYPE_DESC TEXT
        )
    """

    const val CREATE_TABLE_BUSES = """
        CREATE TABLE $TABLE_BUSES (
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_BUS_TYPE_ID INTEGER NOT NULL,
            $COL_BUS_REG_NUMBER TEXT UNIQUE NOT NULL,
            $COL_BUS_MODEL TEXT NOT NULL,
            $COL_BUS_STATUS TEXT DEFAULT 'Active',
            FOREIGN KEY($COL_BUS_TYPE_ID) REFERENCES $TABLE_BUS_TYPES($COL_ID)
        )
    """

    const val CREATE_TABLE_ROUTES = """
        CREATE TABLE $TABLE_ROUTES (
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_ROUTE_ORIGIN TEXT NOT NULL,
            $COL_ROUTE_DESTINATION TEXT NOT NULL,
            $COL_ROUTE_DISTANCE REAL NOT NULL,
            $COL_ROUTE_BASE_FARE REAL NOT NULL,
            $COL_ROUTE_LUGGAGE_FARE REAL NOT NULL,
            $COL_ROUTE_PARCEL_FARE REAL NOT NULL
        )
    """

    const val CREATE_TABLE_TRIPS = """
        CREATE TABLE $TABLE_TRIPS (
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_TRIP_BUS_ID INTEGER NOT NULL,
            $COL_TRIP_ROUTE_ID INTEGER NOT NULL,
            $COL_TRIP_DATE TEXT NOT NULL,
            $COL_TRIP_DEPARTURE_TIME TEXT NOT NULL,
            $COL_TRIP_ARRIVAL_TIME TEXT NOT NULL,
            $COL_TRIP_AVAILABLE_SEATS INTEGER NOT NULL,
            $COL_TRIP_STATUS TEXT DEFAULT 'Scheduled',
            FOREIGN KEY($COL_TRIP_BUS_ID) REFERENCES $TABLE_BUSES($COL_ID),
            FOREIGN KEY($COL_TRIP_ROUTE_ID) REFERENCES $TABLE_ROUTES($COL_ID)
        )
    """

    const val CREATE_TABLE_BOOKINGS = """
        CREATE TABLE $TABLE_BOOKINGS (
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_BOOKING_REF TEXT UNIQUE NOT NULL,
            $COL_BOOKING_TRIP_ID INTEGER NOT NULL,
            $COL_BOOKING_CLERK_ID INTEGER NOT NULL,
            $COL_BOOKING_PASSENGER_NAME TEXT NOT NULL,
            $COL_BOOKING_PHONE TEXT NOT NULL,
            $COL_BOOKING_ID_NUMBER TEXT,
            $COL_BOOKING_TYPE TEXT NOT NULL,
            $COL_BOOKING_SEAT_NUMBER TEXT,
            $COL_BOOKING_AMOUNT REAL NOT NULL,
            $COL_BOOKING_WEIGHT REAL DEFAULT 0,
            $COL_BOOKING_DESC TEXT,
            $COL_BOOKING_STATUS TEXT DEFAULT 'Confirmed',
            $COL_BOOKING_DATE TEXT NOT NULL,
            FOREIGN KEY($COL_BOOKING_TRIP_ID) REFERENCES $TABLE_TRIPS($COL_ID),
            FOREIGN KEY($COL_BOOKING_CLERK_ID) REFERENCES $TABLE_USERS($COL_ID)
        )
    """

    const val CREATE_TABLE_PAYMENTS = """
        CREATE TABLE $TABLE_PAYMENTS (
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_PAYMENT_BOOKING_ID INTEGER NOT NULL,
            $COL_PAYMENT_AMOUNT REAL NOT NULL,
            $COL_PAYMENT_METHOD TEXT NOT NULL,
            $COL_PAYMENT_TRANSACTION_REF TEXT UNIQUE NOT NULL,
            $COL_PAYMENT_STATUS TEXT DEFAULT 'Completed',
            $COL_PAYMENT_DATE TEXT NOT NULL,
            FOREIGN KEY($COL_PAYMENT_BOOKING_ID) REFERENCES $TABLE_BOOKINGS($COL_ID)
        )
    """
}
