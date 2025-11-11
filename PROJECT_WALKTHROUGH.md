# 🚀 Project Walkthrough - Summit Coaches App

## 📱 App Flow Overview

```
SPLASH SCREEN (2 seconds)
    ↓
ONBOARDING (3 swipeable screens)
    ↓
LOGIN or REGISTER
    ↓
DASHBOARD (Main menu)
    ↓
┌─────────────┬──────────────────┬──────────────┐
│             │                  │              │
CREATE        BOOKING          ACCOUNT
BOOKING       HISTORY          DETAILS
│             │                  │
↓             ↓                  ↓
SELECT        VIEW ALL         EDIT
TRIP          BOOKINGS         PROFILE
↓
FILL BOOKING
FORM
↓
CONFIRM &
SAVE
```

## 📂 File Organization

### Package: `com.summitcoaches.app`

```
activities/          → All screens (10 files)
models/             → Data classes (1 file)
database/           → Database logic (2 files)
adapters/           → List adapters (3 files)
utils/              → Helper classes (3 files)
```

### Resources: `res/`

```
layout/             → XML UI files (13 files)
drawable/           → Shapes & backgrounds (3 files)
values/             → Colors, strings, themes (3 files)
mipmap/             → App icons (launcher icons)
```

## 🎬 Screen-by-Screen Walkthrough

### 1. Splash Screen (SplashActivity.kt)

**Purpose:** Show logo and app name for 2 seconds

**Flow:**
1. App opens → Shows logo
2. Waits 2 seconds
3. Goes to Onboarding (first time) or Login (returning user)

**Key Code:**
```kotlin
// Wait 2 seconds then navigate
Handler(Looper.getMainLooper()).postDelayed({
    checkFirstTime()
}, 2000)

// Check if first time opening app
private fun checkFirstTime() {
    val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
    val isFirstTime = prefs.getBoolean("isFirstTime", true)
    
    if (isFirstTime) {
        // Go to onboarding
        startActivity(Intent(this, OnboardingActivity::class.java))
    } else {
        // Go to login
        startActivity(Intent(this, LoginActivity::class.java))
    }
    finish()
}
```

**Layout:** `activity_splash.xml`
- Blue gradient background
- Summit Coaches logo
- App name text
- Tagline text

---

### 2. Onboarding (OnboardingActivity.kt)

**Purpose:** Show 3 intro screens explaining the app

**Features:**
- Swipeable ViewPager2
- 3 screens with icons and descriptions
- Skip button
- Next button → Get Started on last screen
- Indicator dots show current page

**Key Code:**
```kotlin
// Setup ViewPager with adapter
val adapter = OnboardingAdapter(onboardingPages)
viewPager.adapter = adapter

// Handle button clicks
btnNext.setOnClickListener {
    if (currentPage < onboardingPages.size - 1) {
        viewPager.currentItem = currentPage + 1
    } else {
        finishOnboarding()
    }
}

// Save that onboarding is completed
private fun finishOnboarding() {
    val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
    prefs.edit().putBoolean("isFirstTime", false).apply()
    
    startActivity(Intent(this, LoginActivity::class.java))
    finish()
}
```

**Onboarding Pages:**
1. **Easy Booking** - Book tickets quickly
2. **Multiple Options** - Passenger, luggage, parcel
3. **Track History** - View all bookings

**Layout:** `activity_onboarding.xml`
- ViewPager2 for swipe
- Skip button (top right)
- Next/Get Started button
- Dots indicator

---

### 3. Login (LoginActivity.kt)

**Purpose:** Authenticate user to access app

**Features:**
- Email input
- Password input
- Login button
- Forgot password link
- Register link

**Validation:**
```kotlin
private fun validateInputs(): Boolean {
    val email = editEmail.text.toString()
    val password = editPassword.text.toString()
    
    // Check email is not empty
    if (email.isEmpty()) {
        editEmail.error = "Email required"
        return false
    }
    
    // Check email format
    if (!ValidationUtils.isValidEmail(email)) {
        editEmail.error = "Invalid email format"
        return false
    }
    
    // Check password is not empty
    if (password.isEmpty()) {
        editPassword.error = "Password required"
        return false
    }
    
    // Check password length
    if (password.length < 6) {
        editPassword.error = "Password must be at least 6 characters"
        return false
    }
    
    return true
}
```

**Login Process:**
```kotlin
private fun performLogin() {
    if (!validateInputs()) return
    
    val email = editEmail.text.toString()
    val password = editPassword.text.toString()
    
    try {
        // Check credentials in database
        val user = dbHelper.authenticateUser(email, password)
        
        if (user != null) {
            // Save session
            sessionManager.saveUserSession(user.id, user.name, user.email)
            
            // Show success
            Toast.makeText(this, "Welcome ${user.name}!", Toast.LENGTH_SHORT).show()
            
            // Go to dashboard
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        Log.e("LoginActivity", "Login error", e)
    }
}
```

**Layout:** `activity_login.xml`
- Welcome header
- Email input field
- Password input field
- Login button
- Forgot password link
- Register link
- Default credentials info box

---

### 4. Register (RegisterActivity.kt)

**Purpose:** Create new booking clerk account

**Features:**
- Full name input
- Email input
- Phone input
- Password input
- Confirm password input
- Register button
- Login link

**Validation:**
```kotlin
private fun validateInputs(): Boolean {
    val name = editName.text.toString()
    val email = editEmail.text.toString()
    val phone = editPhone.text.toString()
    val password = editPassword.text.toString()
    val confirmPassword = editConfirmPassword.text.toString()
    
    // Validate all fields
    when {
        name.isEmpty() -> {
            editName.error = "Name required"
            return false
        }
        email.isEmpty() -> {
            editEmail.error = "Email required"
            return false
        }
        !ValidationUtils.isValidEmail(email) -> {
            editEmail.error = "Invalid email"
            return false
        }
        phone.isEmpty() -> {
            editPhone.error = "Phone required"
            return false
        }
        !ValidationUtils.isValidPhone(phone) -> {
            editPhone.error = "Invalid phone number"
            return false
        }
        password.isEmpty() -> {
            editPassword.error = "Password required"
            return false
        }
        password.length < 6 -> {
            editPassword.error = "Password must be at least 6 characters"
            return false
        }
        password != confirmPassword -> {
            editConfirmPassword.error = "Passwords do not match"
            return false
        }
        else -> return true
    }
}
```

**Registration Process:**
```kotlin
private fun performRegistration() {
    if (!validateInputs()) return
    
    try {
        val user = User(
            id = 0, // Auto-generated
            name = editName.text.toString(),
            email = editEmail.text.toString(),
            password = editPassword.text.toString(),
            phone = editPhone.text.toString(),
            createdAt = System.currentTimeMillis()
        )
        
        val userId = dbHelper.insertUser(user)
        
        if (userId > 0) {
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
            
            // Go to login
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("email", user.email)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Registration failed. Email may already exist.", Toast.LENGTH_LONG).show()
        }
    } catch (e: Exception) {
        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        Log.e("RegisterActivity", "Registration error", e)
    }
}
```

**Layout:** `activity_register.xml`
- Create account header
- Name input
- Email input
- Phone input
- Password input
- Confirm password input
- Register button
- Login link

---

### 5. Dashboard (DashboardActivity.kt)

**Purpose:** Main menu - hub for all features

**Features:**
- Welcome message with user name
- 4 cards for navigation:
  1. Create Booking → SelectTripActivity
  2. Booking History → BookingHistoryActivity
  3. Account Details → AccountDetailsActivity
  4. Logout → LoginActivity

**Key Code:**
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_dashboard)
    
    // Get current user from session
    val userName = sessionManager.getUserName()
    txtWelcome.text = "Welcome, $userName!"
    
    // Setup card clicks
    cardCreateBooking.setOnClickListener {
        startActivity(Intent(this, SelectTripActivity::class.java))
    }
    
    cardBookingHistory.setOnClickListener {
        startActivity(Intent(this, BookingHistoryActivity::class.java))
    }
    
    cardAccountDetails.setOnClickListener {
        startActivity(Intent(this, AccountDetailsActivity::class.java))
    }
    
    cardLogout.setOnClickListener {
        logout()
    }
}

private fun logout() {
    sessionManager.clearSession()
    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
    
    val intent = Intent(this, LoginActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
    finish()
}
```

**Layout:** `activity_dashboard.xml`
- Blue header with welcome message
- 4 Material CardViews:
  - Each has icon, title, description
  - Click ripple effect
  - Blue accent colors

---

### 6. Select Trip (SelectTripActivity.kt)

**Purpose:** Choose from available scheduled trips

**Features:**
- RecyclerView showing all trips
- Each trip shows:
  - Route (From → To)
  - Date and time
  - Available seats
  - Price
  - Select button
- Filter by route (optional)
- Refresh trips

**Key Code:**
```kotlin
private fun loadTrips() {
    try {
        val trips = dbHelper.getAllTrips()
        
        if (trips.isEmpty()) {
            txtEmptyState.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            txtEmptyState.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            
            // Setup adapter with click listener
            val adapter = TripAdapter(trips) { trip ->
                onTripSelected(trip)
            }
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
    } catch (e: Exception) {
        Toast.makeText(this, "Error loading trips: ${e.message}", Toast.LENGTH_SHORT).show()
        Log.e("SelectTripActivity", "Error loading trips", e)
    }
}

private fun onTripSelected(trip: Trip) {
    val intent = Intent(this, CreateBookingActivity::class.java)
    intent.putExtra("TRIP_ID", trip.id)
    intent.putExtra("TRIP_ROUTE", "${trip.fromLocation} → ${trip.toLocation}")
    intent.putExtra("TRIP_PRICE", trip.price)
    intent.putExtra("TRIP_DATE", trip.departureDate)
    intent.putExtra("TRIP_TIME", trip.departureTime)
    startActivity(intent)
}
```

**Trip Adapter:**
```kotlin
class TripAdapter(
    private val trips: List<Trip>,
    private val onTripClick: (Trip) -> Unit
) : RecyclerView.Adapter<TripAdapter.ViewHolder>() {
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trip = trips[position]
        
        holder.txtRoute.text = "${trip.fromLocation} → ${trip.toLocation}"
        holder.txtDateTime.text = "${trip.departureDate} at ${trip.departureTime}"
        holder.txtSeats.text = "${trip.availableSeats} seats available"
        holder.txtPrice.text = "UGX ${String.format("%,d", trip.price.toInt())}"
        
        holder.btnSelect.setOnClickListener {
            onTripClick(trip)
        }
    }
}
```

**Layout:** `activity_select_trip.xml`
- Header "Available Trips"
- RecyclerView for trip list
- Empty state text (if no trips)
- Each trip uses `item_trip.xml` layout

---

### 7. Create Booking (CreateBookingActivity.kt)

**Purpose:** Create new booking with customer details

**Features:**
- Shows selected trip info
- Booking type selection (Passenger/Luggage/Parcel)
- Customer information form:
  - Name
  - Phone
  - ID Number (optional)
  - Seat number (for passenger)
- Calculate total price
- Confirm and save booking

**Key Code:**
```kotlin
private fun setupBookingTypeSpinner() {
    val bookingTypes = dbHelper.getAllBookingTypes()
    val typeNames = bookingTypes.map { it.name }
    
    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, typeNames)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinnerBookingType.adapter = adapter
    
    spinnerBookingType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            selectedBookingType = bookingTypes[position]
            calculatePrice()
            
            // Show/hide seat number based on type
            if (selectedBookingType.name == "PASSENGER") {
                layoutSeatNumber.visibility = View.VISIBLE
            } else {
                layoutSeatNumber.visibility = View.GONE
            }
        }
        
        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
}

private fun calculatePrice() {
    val tripPrice = intent.getDoubleExtra("TRIP_PRICE", 0.0)
    val basePrice = selectedBookingType.basePrice
    
    // Price logic (can be customized)
    totalAmount = when (selectedBookingType.name) {
        "PASSENGER" -> tripPrice
        "LUGGAGE" -> basePrice
        "PARCEL" -> basePrice
        else -> tripPrice
    }
    
    txtTotalPrice.text = "UGX ${String.format("%,d", totalAmount.toInt())}"
}

private fun saveBooking() {
    if (!validateInputs()) return
    
    try {
        val booking = Booking(
            id = 0,
            bookingReference = generateBookingReference(),
            tripId = tripId,
            bookingTypeId = selectedBookingType.id,
            userId = sessionManager.getUserId(),
            customerName = editCustomerName.text.toString(),
            customerPhone = editCustomerPhone.text.toString(),
            customerIdNumber = editIdNumber.text.toString(),
            seatNumber = if (selectedBookingType.name == "PASSENGER") 
                         editSeatNumber.text.toString() else "",
            amount = totalAmount,
            status = "CONFIRMED",
            createdAt = System.currentTimeMillis()
        )
        
        val bookingId = dbHelper.insertBooking(booking)
        
        if (bookingId > 0) {
            // Create payment record
            createPayment(bookingId)
            
            Toast.makeText(this, "Booking created successfully!", Toast.LENGTH_SHORT).show()
            
            // Go to booking history
            val intent = Intent(this, BookingHistoryActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    } catch (e: Exception) {
        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        Log.e("CreateBookingActivity", "Error creating booking", e)
    }
}

private fun generateBookingReference(): String {
    return "SC${System.currentTimeMillis()}"
}
```

**Layout:** `activity_create_booking.xml`
- Trip info card (read-only)
- Booking type spinner
- Customer info form
- Total price display
- Confirm booking button

---

### 8. Booking History (BookingHistoryActivity.kt)

**Purpose:** View all bookings created by current clerk

**Features:**
- RecyclerView showing all bookings
- Each booking shows:
  - Reference number
  - Route
  - Customer name
  - Date & time
  - Status badge (Confirmed/Pending/Cancelled)
  - Amount
- Pull to refresh
- Empty state if no bookings

**Key Code:**
```kotlin
private fun loadBookings() {
    try {
        val userId = sessionManager.getUserId()
        val bookings = dbHelper.getBookingsByUserId(userId)
        
        if (bookings.isEmpty()) {
            txtEmptyState.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            txtEmptyState.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            
            val adapter = BookingAdapter(bookings)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
    } catch (e: Exception) {
        Toast.makeText(this, "Error loading bookings: ${e.message}", Toast.LENGTH_SHORT).show()
        Log.e("BookingHistoryActivity", "Error loading bookings", e)
    }
}
```

**Booking Adapter:**
```kotlin
class BookingAdapter(private val bookings: List<Booking>) : 
    RecyclerView.Adapter<BookingAdapter.ViewHolder>() {
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val booking = bookings[position]
        
        holder.txtReference.text = booking.bookingReference
        holder.txtRoute.text = booking.route
        holder.txtCustomer.text = booking.customerName
        holder.txtDateTime.text = "${booking.date} at ${booking.time}"
        holder.txtAmount.text = "UGX ${String.format("%,d", booking.amount.toInt())}"
        
        // Set status badge
        when (booking.status) {
            "CONFIRMED" -> {
                holder.txtStatus.text = "Confirmed"
                holder.txtStatus.setBackgroundResource(R.drawable.bg_status_confirmed)
            }
            "PENDING" -> {
                holder.txtStatus.text = "Pending"
                holder.txtStatus.setBackgroundResource(R.drawable.bg_status_pending)
            }
            "CANCELLED" -> {
                holder.txtStatus.text = "Cancelled"
                holder.txtStatus.setBackgroundResource(R.drawable.bg_status_cancelled)
            }
        }
    }
}
```

**Layout:** `activity_booking_history.xml`
- Header "Booking History"
- RecyclerView for bookings list
- Empty state text
- Each booking uses `item_booking.xml`

---

### 9. Account Details (AccountDetailsActivity.kt)

**Purpose:** View and edit booking clerk profile

**Features:**
- Display current user info
- Edit name
- Edit phone
- Change password
- Save changes button

**Key Code:**
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_account_details)
    
    loadUserDetails()
    
    btnSave.setOnClickListener {
        saveChanges()
    }
}

private fun loadUserDetails() {
    val userId = sessionManager.getUserId()
    val user = dbHelper.getUserById(userId)
    
    if (user != null) {
        editName.setText(user.name)
        editEmail.setText(user.email)
        editEmail.isEnabled = false // Email cannot be changed
        editPhone.setText(user.phone)
    }
}

private fun saveChanges() {
    if (!validateInputs()) return
    
    try {
        val userId = sessionManager.getUserId()
        val name = editName.text.toString()
        val phone = editPhone.text.toString()
        val currentPassword = editCurrentPassword.text.toString()
        val newPassword = editNewPassword.text.toString()
        
        // Verify current password if changing password
        if (newPassword.isNotEmpty()) {
            val user = dbHelper.getUserById(userId)
            if (user?.password != currentPassword) {
                Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show()
                return
            }
        }
        
        val success = dbHelper.updateUser(userId, name, phone, newPassword)
        
        if (success) {
            sessionManager.updateUserName(name)
            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        Log.e("AccountDetailsActivity", "Error updating user", e)
    }
}
```

**Layout:** `activity_account_details.xml`
- Profile header
- Name input (editable)
- Email input (read-only)
- Phone input (editable)
- Current password input
- New password input
- Save button

---

### 10. Forgot Password (ForgotPasswordActivity.kt)

**Purpose:** Reset password (simplified version)

**Features:**
- Email input
- Security question (if implemented)
- Reset password button
- Back to login link

**Note:** This is a simplified version. In production, you'd use email verification or SMS.

**Key Code:**
```kotlin
private fun resetPassword() {
    val email = editEmail.text.toString()
    
    if (email.isEmpty()) {
        editEmail.error = "Email required"
        return
    }
    
    try {
        val user = dbHelper.getUserByEmail(email)
        
        if (user != null) {
            // In real app: send email with reset link
            // For now: show temporary password
            Toast.makeText(
                this, 
                "Password reset instructions sent to $email", 
                Toast.LENGTH_LONG
            ).show()
            
            finish()
        } else {
            Toast.makeText(this, "Email not found", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        Log.e("ForgotPasswordActivity", "Error resetting password", e)
    }
}
```

**Layout:** `activity_forgot_password.xml`
- Forgot password header
- Email input
- Reset button
- Back to login link

---

## 🗂️ Data Models

### User
```kotlin
data class User(
    val id: Long,
    val name: String,
    val email: String,
    val password: String,
    val phone: String,
    val createdAt: Long
)
```

### Trip
```kotlin
data class Trip(
    val id: Long,
    val busId: Long,
    val routeId: Long,
    val fromLocation: String,
    val toLocation: String,
    val departureTime: String,
    val departureDate: String,
    val price: Double,
    val availableSeats: Int,
    val busPlateNumber: String,
    val busTypeName: String
)
```

### Booking
```kotlin
data class Booking(
    val id: Long,
    val bookingReference: String,
    val tripId: Long,
    val bookingTypeId: Long,
    val userId: Long,
    val customerName: String,
    val customerPhone: String,
    val customerIdNumber: String,
    val seatNumber: String,
    val amount: Double,
    val status: String,
    val createdAt: Long
)
```

## 🔧 Helper Classes

### 1. SessionManager
Manages user login session

```kotlin
class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
    
    fun saveUserSession(userId: Long, name: String, email: String) {
        prefs.edit().apply {
            putLong("user_id", userId)
            putString("user_name", name)
            putString("user_email", email)
            putBoolean("is_logged_in", true)
            apply()
        }
    }
    
    fun isLoggedIn() = prefs.getBoolean("is_logged_in", false)
    
    fun getUserId() = prefs.getLong("user_id", 0)
    
    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
```

### 2. ValidationUtils
Validates user inputs

```kotlin
object ValidationUtils {
    fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }
    
    fun isValidPhone(phone: String): Boolean {
        return phone.length >= 10 && phone.all { it.isDigit() }
    }
    
    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
}
```

### 3. Constants
App-wide constants

```kotlin
object Constants {
    const val PREF_NAME = "AppPrefs"
    const val IS_FIRST_TIME = "isFirstTime"
    const val USER_SESSION = "UserSession"
    
    const val STATUS_CONFIRMED = "CONFIRMED"
    const val STATUS_PENDING = "PENDING"
    const val STATUS_CANCELLED = "CANCELLED"
    
    const val TYPE_PASSENGER = "PASSENGER"
    const val TYPE_LUGGAGE = "LUGGAGE"
    const val TYPE_PARCEL = "PARCEL"
}
```

## 🎨 Design System

### Colors (`res/values/colors.xml`)
```xml
<color name="blue_primary">#1976D2</color>
<color name="blue_dark">#0D47A1</color>
<color name="background">#F5F5F5</color>
<color name="text_primary">#212121</color>
<color name="text_secondary">#757575</color>
```

### Theme (`res/values/themes.xml`)
- Material Design 3
- Blue color scheme
- Dark status bar

## 🐛 Error Handling

All activities use try-catch blocks:

```kotlin
try {
    // Database operations
} catch (e: Exception) {
    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
    Log.e("TAG", "Error message", e)
}
```

## 📝 Key Exam Points

### What You Should Know:

1. **App Architecture**
   - Activities = Screens
   - Layouts = UI design
   - Models = Data structures
   - Database = Data storage

2. **Navigation Flow**
   - Intents navigate between activities
   - Bundle passes data
   - finish() closes activity

3. **Database Operations**
   - Create with INSERT
   - Read with SELECT/query()
   - Update with UPDATE
   - Delete with DELETE

4. **UI Components**
   - EditText for input
   - Button for actions
   - RecyclerView for lists
   - CardView for cards

5. **Data Flow**
   ```
   User Input → Validation → Database → Display Result
   ```

---

**You're ready for your exam! Good luck!** 🎓
