package com.summitcoaches.app.activities

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.summitcoaches.app.R
import com.summitcoaches.app.database.DatabaseHelper
import com.summitcoaches.app.models.*
import com.summitcoaches.app.utils.SessionManager
import com.summitcoaches.app.utils.ValidationUtils
import java.text.SimpleDateFormat
import java.util.*

class CreateBookingActivity : AppCompatActivity() {

    private lateinit var spinnerBookingType: Spinner
    private lateinit var etPassengerName: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etIdNumber: EditText
    private lateinit var etSeatNumber: EditText
    private lateinit var etWeight: EditText
    private lateinit var etParcelDescription: EditText
    private lateinit var layoutPassengerFields: LinearLayout
    private lateinit var layoutLuggageFields: LinearLayout
    private lateinit var layoutParcelFields: LinearLayout
    private lateinit var btnCreateBooking: Button
    private lateinit var tvTripDetails: TextView
    private lateinit var tvTotalAmount: TextView
    
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager
    private var selectedTrip: Trip? = null
    private var selectedBookingType = "Passenger"
    private var tripId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_booking)

        supportActionBar?.title = "Create Booking"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = DatabaseHelper(this)
        sessionManager = SessionManager(this)
        tripId = intent.getIntExtra("TRIP_ID", 0)

        initializeViews()
        loadTripDetails()
        setupListeners()
    }

    private fun initializeViews() {
        spinnerBookingType = findViewById(R.id.spinnerBookingType)
        etPassengerName = findViewById(R.id.etPassengerName)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etIdNumber = findViewById(R.id.etIdNumber)
        etSeatNumber = findViewById(R.id.etSeatNumber)
        etWeight = findViewById(R.id.etWeight)
        etParcelDescription = findViewById(R.id.etParcelDescription)
        layoutPassengerFields = findViewById(R.id.layoutPassengerFields)
        layoutLuggageFields = findViewById(R.id.layoutLuggageFields)
        layoutParcelFields = findViewById(R.id.layoutParcelFields)
        btnCreateBooking = findViewById(R.id.btnCreateBooking)
        tvTripDetails = findViewById(R.id.tvTripDetails)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)

        val bookingTypes = arrayOf("Passenger", "Luggage", "Parcel")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bookingTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerBookingType.adapter = adapter
    }

    private fun loadTripDetails() {
        selectedTrip = dbHelper.getTripById(tripId)
        selectedTrip?.let { trip ->
            val route = dbHelper.getRouteById(trip.routeId)
            val bus = dbHelper.getBusById(trip.busId)
            
            tvTripDetails.text = """
                Route: ${route?.origin} → ${route?.destination}
                Bus: ${bus?.registrationNumber}
                Date: ${trip.tripDate}
                Time: ${trip.departureTime}
                Available Seats: ${trip.availableSeats}
            """.trimIndent()
            
            calculateAmount()
        }
    }

    private fun setupListeners() {
        spinnerBookingType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedBookingType = parent?.getItemAtPosition(position).toString()
                updateVisibleFields()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        btnCreateBooking.setOnClickListener {
            createBooking()
        }
    }

    private fun updateVisibleFields() {
        when (selectedBookingType) {
            "Passenger" -> {
                layoutPassengerFields.visibility = View.VISIBLE
                layoutLuggageFields.visibility = View.GONE
                layoutParcelFields.visibility = View.GONE
            }
            "Luggage" -> {
                layoutPassengerFields.visibility = View.VISIBLE
                layoutLuggageFields.visibility = View.VISIBLE
                layoutParcelFields.visibility = View.GONE
            }
            "Parcel" -> {
                layoutPassengerFields.visibility = View.VISIBLE
                layoutLuggageFields.visibility = View.GONE
                layoutParcelFields.visibility = View.VISIBLE
            }
        }
        calculateAmount()
    }

    private fun calculateAmount() {
        selectedTrip?.let { trip ->
            val route = dbHelper.getRouteById(trip.routeId)
            route?.let {
                val amount = when (selectedBookingType) {
                    "Passenger" -> it.baseFare
                    "Luggage" -> it.luggageFare
                    "Parcel" -> it.parcelFare
                    else -> 0.0
                }
                tvTotalAmount.text = "Total: UGX ${String.format("%.2f", amount)}"
            }
        }
    }

    private fun createBooking() {
        if (!validateInputs()) return

        selectedTrip?.let { trip ->
            val route = dbHelper.getRouteById(trip.routeId)
            val amount = when (selectedBookingType) {
                "Passenger" -> route?.baseFare ?: 0.0
                "Luggage" -> route?.luggageFare ?: 0.0
                "Parcel" -> route?.parcelFare ?: 0.0
                else -> 0.0
            }

            val bookingRef = "SC${System.currentTimeMillis()}"
            val booking = Booking(
                id = 0,
                bookingReference = bookingRef,
                tripId = trip.id,
                clerkId = sessionManager.getUserId(),
                passengerName = etPassengerName.text.toString().trim(),
                phoneNumber = etPhoneNumber.text.toString().trim(),
                idNumber = etIdNumber.text.toString().trim(),
                bookingType = selectedBookingType,
                seatNumber = if (selectedBookingType == "Passenger") etSeatNumber.text.toString().trim() else "",
                amount = amount,
                weight = if (selectedBookingType != "Passenger") etWeight.text.toString().toDoubleOrNull() ?: 0.0 else 0.0,
                description = if (selectedBookingType == "Parcel") etParcelDescription.text.toString().trim() else "",
                status = "Confirmed",
                bookingDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            )

            val bookingId = dbHelper.createBooking(booking)

            if (bookingId > 0) {
                val payment = Payment(
                    id = 0,
                    bookingId = bookingId.toInt(),
                    amount = amount,
                    paymentMethod = "Cash",
                    transactionRef = "TXN${System.currentTimeMillis()}",
                    status = "Completed",
                    paymentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                )
                dbHelper.createPayment(payment)

                trip.availableSeats = trip.availableSeats - 1
                dbHelper.updateTrip(trip)

                Toast.makeText(this, "Booking created!\nReference: $bookingRef", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to create booking", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInputs(): Boolean {
        val passengerName = etPassengerName.text.toString().trim()
        if (passengerName.isEmpty()) {
            etPassengerName.error = "Name is required"
            return false
        }

        val phoneNumber = etPhoneNumber.text.toString().trim()
        if (!ValidationUtils.isValidPhone(phoneNumber)) {
            etPhoneNumber.error = "Invalid phone number"
            return false
        }

        when (selectedBookingType) {
            "Passenger" -> {
                if (etSeatNumber.text.toString().trim().isEmpty()) {
                    etSeatNumber.error = "Seat number is required"
                    return false
                }
            }
            "Luggage" -> {
                if (etWeight.text.toString().trim().isEmpty()) {
                    etWeight.error = "Weight is required"
                    return false
                }
            }
            "Parcel" -> {
                if (etParcelDescription.text.toString().trim().isEmpty()) {
                    etParcelDescription.error = "Description is required"
                    return false
                }
            }
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
