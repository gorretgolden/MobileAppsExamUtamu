# 🗄️ Database Guide - SQLite for Summit Coaches

## 📘 What is a Database?

A database is like a digital filing cabinet that stores information in organized tables.

### Why Use a Database?

- 📦 Store lots of data efficiently
- 🔍 Search data quickly
- 📊 Keep data organized
- 💾 Save data permanently (even when app closes)
- 🔄 Update data easily

## 🆚 SQLite vs MySQL

### MySQL (What you asked for)
- ❌ Needs a server computer running 24/7
- ❌ Needs internet connection
- ❌ Needs backend API (PHP/Node.js/etc)
- ❌ Costs money for hosting
- ❌ Complex setup
- ✅ Good for websites

### SQLite (What we're using)
- ✅ Built into Android (no installation)
- ✅ Works offline (no internet needed)
- ✅ Free forever
- ✅ Easy to use
- ✅ Fast and lightweight
- ✅ Perfect for mobile apps
- ✅ Exactly what Android is designed for

### Why SQLite is Better for Mobile

Imagine MySQL like a library:
- You need to travel to the library (internet)
- Library has opening hours (server uptime)
- Costs money to maintain (hosting)

SQLite is like books on your shelf:
- Always available
- No travel needed
- Free to use
- Works anywhere

## 📊 Database Structure

### Our Database: `summit_coaches.db`

The app has **8 tables** that store all data:

### 1. **users** Table
Stores booking clerk accounts

| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER | Unique ID (auto-increment) |
| name | TEXT | Full name |
| email | TEXT | Email address (unique) |
| password | TEXT | Password (encrypted) |
| phone | TEXT | Phone number |
| created_at | INTEGER | When account was created |

**Example Data:**
```
id: 1
name: John Doe
email: clerk@summitcoaches.com
password: (encrypted)
phone: 0700000000
created_at: 1699564800
```

### 2. **bus_types** Table
Types of buses available

| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER | Unique ID |
| name | TEXT | Type name (e.g., "VIP", "Regular") |
| description | TEXT | Description of type |

**Example Data:**
```
id: 1
name: VIP
description: Luxury bus with AC and WiFi
```

### 3. **buses** Table
Individual bus vehicles

| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER | Unique ID |
| plate_number | TEXT | License plate (unique) |
| bus_type_id | INTEGER | Links to bus_types table |
| capacity | INTEGER | Number of seats |

**Example Data:**
```
id: 1
plate_number: UAM 123A
bus_type_id: 1 (VIP)
capacity: 50
```

### 4. **routes** Table
Travel routes between cities

| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER | Unique ID |
| from_location | TEXT | Starting city |
| to_location | TEXT | Destination city |
| distance_km | INTEGER | Distance in kilometers |
| duration_hours | REAL | Travel time in hours |

**Example Data:**
```
id: 1
from_location: Kampala
to_location: Mbarara
distance_km: 270
duration_hours: 4.5
```

### 5. **trips** Table
Scheduled bus trips

| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER | Unique ID |
| bus_id | INTEGER | Which bus (links to buses) |
| route_id | INTEGER | Which route (links to routes) |
| departure_time | TEXT | When bus leaves |
| departure_date | TEXT | Date of trip |
| price | REAL | Ticket price |
| available_seats | INTEGER | Seats still available |

**Example Data:**
```
id: 1
bus_id: 1
route_id: 1 (Kampala → Mbarara)
departure_time: 08:00 AM
departure_date: 2025-11-08
price: 45000
available_seats: 48
```

### 6. **booking_types** Table
Types of bookings possible

| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER | Unique ID |
| name | TEXT | Type name |
| description | TEXT | Description |
| base_price | REAL | Starting price |

**Example Data:**
```
id: 1
name: PASSENGER
description: Regular passenger booking
base_price: 45000
```

### 7. **bookings** Table
Customer bookings

| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER | Unique ID |
| booking_reference | TEXT | Unique code (e.g., SC1234567) |
| trip_id | INTEGER | Which trip (links to trips) |
| booking_type_id | INTEGER | Type (links to booking_types) |
| user_id | INTEGER | Clerk who booked (links to users) |
| customer_name | TEXT | Customer's name |
| customer_phone | TEXT | Customer's phone |
| customer_id_number | TEXT | ID number (optional) |
| seat_number | TEXT | Assigned seat |
| amount | REAL | Total amount paid |
| status | TEXT | CONFIRMED, PENDING, CANCELLED |
| created_at | INTEGER | When booked |

**Example Data:**
```
id: 1
booking_reference: SC1730889234567
trip_id: 1
booking_type_id: 1 (PASSENGER)
user_id: 1 (John Doe clerk)
customer_name: Jane Smith
customer_phone: 0750000000
seat_number: A12
amount: 45000
status: CONFIRMED
created_at: 1730889234
```

### 8. **payments** Table
Payment records

| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER | Unique ID |
| booking_id | INTEGER | Which booking (links to bookings) |
| amount | REAL | Amount paid |
| payment_method | TEXT | CASH, MOBILE_MONEY, etc |
| payment_reference | TEXT | Payment reference |
| status | TEXT | COMPLETED, PENDING, FAILED |
| created_at | INTEGER | When paid |

**Example Data:**
```
id: 1
booking_id: 1
amount: 45000
payment_method: CASH
payment_reference: PAY1730889234
status: COMPLETED
created_at: 1730889234
```

## 🔗 Table Relationships

### How Tables Connect

```
users (clerk)
    ↓ (creates)
bookings
    ↓ (for)
trips
    ↓ (uses)
buses → bus_types
    ↓ (follows)
routes

bookings
    ↓ (has)
booking_types
    ↓ (generates)
payments
```

### Example: Complete Booking Flow

1. **Clerk** (John Doe) logs in → `users` table
2. Selects a **trip** → `trips` table
3. Trip uses a **bus** → `buses` table
4. Bus is of **type** VIP → `bus_types` table
5. Trip follows a **route** → `routes` table (Kampala → Mbarara)
6. Creates **booking** for customer → `bookings` table
7. Booking is of **type** PASSENGER → `booking_types` table
8. Records **payment** → `payments` table

## 💻 SQL Commands

### CREATE TABLE

```sql
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    phone TEXT,
    created_at INTEGER
);
```

### INSERT Data

```sql
INSERT INTO users (name, email, password, phone, created_at)
VALUES ('John Doe', 'john@email.com', 'password123', '0700000000', 1699564800);
```

### SELECT (Read) Data

```sql
-- Get all users
SELECT * FROM users;

-- Get specific user
SELECT * FROM users WHERE email = 'john@email.com';

-- Get user with password
SELECT * FROM users 
WHERE email = 'john@email.com' AND password = 'password123';

-- Get bookings with trip info (JOIN)
SELECT b.*, t.departure_time, r.from_location, r.to_location
FROM bookings b
JOIN trips t ON b.trip_id = t.id
JOIN routes r ON t.route_id = r.id
WHERE b.user_id = 1;
```

### UPDATE Data

```sql
UPDATE users 
SET name = 'Jane Doe', phone = '0750000000'
WHERE id = 1;
```

### DELETE Data

```sql
DELETE FROM bookings 
WHERE id = 1;
```

## 🔧 How Database Works in Our App

### 1. DatabaseHelper Class

This class manages all database operations:

```kotlin
class DatabaseHelper(context: Context) : 
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    
    // Called when database is created first time
    override fun onCreate(db: SQLiteDatabase) {
        // Create all tables
        createTables(db)
        // Insert sample data
        insertSampleData(db)
    }
    
    // Called when database version increases
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop old tables
        // Create new tables
    }
}
```

### 2. Insert Operation

```kotlin
// Kotlin code to insert user
fun insertUser(user: User): Long {
    val db = writableDatabase
    
    val values = ContentValues().apply {
        put(COLUMN_NAME, user.name)
        put(COLUMN_EMAIL, user.email)
        put(COLUMN_PASSWORD, user.password)
        put(COLUMN_PHONE, user.phone)
        put(COLUMN_CREATED_AT, System.currentTimeMillis())
    }
    
    return db.insert(TABLE_USERS, null, values)
}
```

### 3. Query Operation

```kotlin
// Kotlin code to get user by email
fun getUserByEmail(email: String): User? {
    val db = readableDatabase
    
    val cursor = db.query(
        TABLE_USERS,                           // Table
        null,                                  // All columns
        "$COLUMN_EMAIL=?",                     // WHERE clause
        arrayOf(email),                        // WHERE values
        null, null, null
    )
    
    var user: User? = null
    if (cursor.moveToFirst()) {
        user = User(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
            name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
            email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
            password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
            phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
            createdAt = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT))
        )
    }
    cursor.close()
    
    return user
}
```

### 4. Update Operation

```kotlin
// Kotlin code to update user
fun updateUser(user: User): Int {
    val db = writableDatabase
    
    val values = ContentValues().apply {
        put(COLUMN_NAME, user.name)
        put(COLUMN_EMAIL, user.email)
        put(COLUMN_PHONE, user.phone)
    }
    
    return db.update(
        TABLE_USERS,
        values,
        "$COLUMN_ID=?",
        arrayOf(user.id.toString())
    )
}
```

### 5. Delete Operation

```kotlin
// Kotlin code to delete booking
fun deleteBooking(bookingId: Long): Int {
    val db = writableDatabase
    
    return db.delete(
        TABLE_BOOKINGS,
        "$COLUMN_ID=?",
        arrayOf(bookingId.toString())
    )
}
```

## 📱 Using Database in Activities

### Login Example

```kotlin
class LoginActivity : AppCompatActivity() {
    
    private lateinit var dbHelper: DatabaseHelper
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        // Initialize database
        dbHelper = DatabaseHelper(this)
        
        btnLogin.setOnClickListener {
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            
            // Query database
            val user = dbHelper.authenticateUser(email, password)
            
            if (user != null) {
                // Login successful
                Toast.makeText(this, "Welcome ${user.name}!", Toast.LENGTH_SHORT).show()
                
                // Go to dashboard
                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra("USER_ID", user.id)
                intent.putExtra("USER_NAME", user.name)
                startActivity(intent)
                finish()
            } else {
                // Login failed
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
```

### Create Booking Example

```kotlin
class CreateBookingActivity : AppCompatActivity() {
    
    private lateinit var dbHelper: DatabaseHelper
    
    private fun saveBooking() {
        val booking = Booking(
            id = 0, // Auto-generated
            bookingReference = generateReference(),
            tripId = selectedTripId,
            bookingTypeId = selectedTypeId,
            userId = currentUserId,
            customerName = editCustomerName.text.toString(),
            customerPhone = editCustomerPhone.text.toString(),
            customerIdNumber = editIdNumber.text.toString(),
            seatNumber = editSeatNumber.text.toString(),
            amount = calculatedAmount,
            status = "CONFIRMED",
            createdAt = System.currentTimeMillis()
        )
        
        val bookingId = dbHelper.insertBooking(booking)
        
        if (bookingId > 0) {
            Toast.makeText(this, "Booking created successfully!", Toast.LENGTH_SHORT).show()
            
            // Navigate to booking history
            val intent = Intent(this, BookingHistoryActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Failed to create booking", Toast.LENGTH_SHORT).show()
        }
    }
}
```

## 🎯 Pre-loaded Sample Data

The app comes with sample data already inserted:

### Users (Clerks)
```
1. clerk@summitcoaches.com / clerk123
2. john@email.com / password123
```

### Bus Types
```
1. VIP - Luxury bus with AC and WiFi
2. Regular - Standard comfortable bus
3. Economy - Budget-friendly option
```

### Buses
```
1. UAM 123A (VIP, 50 seats)
2. UAM 456B (Regular, 60 seats)
3. UAM 789C (VIP, 50 seats)
4. UAM 321D (Economy, 70 seats)
5. UAM 654E (Regular, 60 seats)
```

### Routes
```
1. Kampala → Mbarara (270 km, 4.5 hours)
2. Kampala → Gulu (330 km, 5.5 hours)
3. Kampala → Mbale (245 km, 4 hours)
4. Kampala → Fort Portal (300 km, 5 hours)
```

### Trips (10 scheduled trips)
- Various times and dates
- Different buses and routes
- Prices from 35,000 to 55,000 UGX

### Booking Types
```
1. PASSENGER - 45,000 UGX base price
2. LUGGAGE - 15,000 UGX base price
3. PARCEL - 10,000 UGX base price
```

## 🔍 Viewing Database

### Method 1: Using Android Studio

1. Run the app on emulator
2. Open **Device File Explorer** (bottom right)
3. Navigate to: `/data/data/com.summitcoaches.app/databases/`
4. Find `summit_coaches.db`
5. Right-click → **Save As**
6. Open with **DB Browser for SQLite** (download from sqlitebrowser.org)

### Method 2: Using Logcat

Add logging to see data:

```kotlin
// In DatabaseHelper
fun getAllUsers() {
    val db = readableDatabase
    val cursor = db.query(TABLE_USERS, null, null, null, null, null, null)
    
    while (cursor.moveToNext()) {
        val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
        Log.d("DATABASE", "User: $name - $email")
    }
    cursor.close()
}
```

## 🐛 Common Database Issues

### Issue 1: Database Locked

**Cause:** Forgot to close cursor or database
**Solution:**
```kotlin
// Always close cursor
cursor.close()

// Always close database when done
db.close()
```

### Issue 2: Table Not Found

**Cause:** Database not created or wrong table name
**Solution:**
- Uninstall app
- Reinstall (recreates database)
- Check table name spelling

### Issue 3: Column Not Found

**Cause:** Column name typo
**Solution:**
```kotlin
// Use constants instead of strings
companion object {
    const val COLUMN_NAME = "name"
    const val COLUMN_EMAIL = "email"
}

// Then use:
cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
```

### Issue 4: Constraint Failed

**Cause:** Trying to insert duplicate unique value
**Solution:**
- Check if email already exists before inserting
- Use proper error handling with try-catch

## 📚 Key Takeaways

1. **SQLite is better than MySQL** for mobile apps
2. **Tables store data** in organized rows and columns
3. **Relationships connect** tables together
4. **CRUD operations**: Create, Read, Update, Delete
5. **Always close** cursors and database connections
6. **Use constants** for table and column names
7. **Handle errors** with try-catch blocks

## 🎓 Next Steps

1. Open **DatabaseHelper.kt** and read the code
2. Look at how tables are created in `onCreate()`
3. Find where sample data is inserted
4. See how `insertUser()` works
5. Understand how `authenticateUser()` queries data
6. Explore other CRUD operations

---

**You now understand databases! Ready to build features!** 🗄️
