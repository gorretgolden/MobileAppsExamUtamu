# 📘 Kotlin Basics for Beginners

## What is Kotlin?

Kotlin is a modern programming language for Android apps. It's easier and safer than Java!

## 🎯 Basic Syntax

### 1. Variables

**val - Read Only (Cannot change)**
```kotlin
val appName = "Summit Coaches"  // Cannot change
val maxSeats = 50                // Cannot change
```

**var - Mutable (Can change)**
```kotlin
var userName = "John"            // Can change
var seatCount = 0                // Can change
seatCount = 5                    // Now it's 5
```

**Types:**
```kotlin
val name: String = "John"        // Text
val age: Int = 25                // Whole number
val price: Double = 45.50        // Decimal
val isBooked: Boolean = true     // true or false
val date: Long = 1234567890L     // Big number
```

### 2. Null Safety

Kotlin protects you from null errors!

```kotlin
// Cannot be null
var name: String = "John"
name = null  // ❌ ERROR!

// Can be null (add ?)
var name: String? = "John"
name = null  // ✅ OK!

// Safe call (?.)
val length = name?.length  // Returns null if name is null

// Elvis operator (?:)
val length = name?.length ?: 0  // Returns 0 if name is null
```

### 3. Functions

```kotlin
// Simple function
fun sayHello() {
    println("Hello!")
}

// Function with parameters
fun greet(name: String) {
    println("Hello, $name!")
}

// Function that returns value
fun add(a: Int, b: Int): Int {
    return a + b
}

// Short version
fun multiply(a: Int, b: Int) = a * b

// Using functions
sayHello()           // Prints: Hello!
greet("John")        // Prints: Hello, John!
val sum = add(5, 3)  // sum = 8
```

### 4. String Templates

```kotlin
val name = "John"
val age = 25

// Embed variables in strings with $
println("Name: $name")              // Name: John
println("Age: $age")                // Age: 25
println("Next year: ${age + 1}")    // Next year: 26
```

### 5. Conditions (if/else)

```kotlin
val age = 18

// Simple if
if (age >= 18) {
    println("Adult")
}

// if-else
if (age >= 18) {
    println("Adult")
} else {
    println("Minor")
}

// if as expression
val status = if (age >= 18) "Adult" else "Minor"

// Multiple conditions
if (age < 13) {
    println("Child")
} else if (age < 18) {
    println("Teen")
} else {
    println("Adult")
}
```

### 6. When Expression (like switch)

```kotlin
val bookingType = "PASSENGER"

when (bookingType) {
    "PASSENGER" -> println("Booking passenger")
    "LUGGAGE" -> println("Booking luggage")
    "PARCEL" -> println("Booking parcel")
    else -> println("Unknown type")
}

// When with return
val price = when (bookingType) {
    "PASSENGER" -> 45000
    "LUGGAGE" -> 15000
    "PARCEL" -> 10000
    else -> 0
}
```

### 7. Lists

```kotlin
// Read-only list
val cities = listOf("Kampala", "Mbarara", "Gulu")
println(cities[0])           // Kampala
println(cities.size)         // 3

// Mutable list (can change)
val bookings = mutableListOf<String>()
bookings.add("Booking 1")
bookings.add("Booking 2")
bookings.remove("Booking 1")

// Loop through list
for (city in cities) {
    println(city)
}

// Loop with index
for ((index, city) in cities.withIndex()) {
    println("$index: $city")
}
```

### 8. Classes

```kotlin
// Simple class
class Bus {
    var plateNumber = ""
    var capacity = 0
}

// Create object
val bus = Bus()
bus.plateNumber = "UAM 123A"
bus.capacity = 50

// Class with constructor
class Bus(val plateNumber: String, val capacity: Int)

// Create object (shorter)
val bus = Bus("UAM 123A", 50)
println(bus.plateNumber)  // UAM 123A

// Class with methods
class Bus(val plateNumber: String, var capacity: Int) {
    
    fun showInfo() {
        println("Bus: $plateNumber, Seats: $capacity")
    }
    
    fun isFull(): Boolean {
        return capacity == 0
    }
}
```

### 9. Data Classes

Perfect for holding data!

```kotlin
data class User(
    val id: Long,
    val name: String,
    val email: String,
    val phone: String
)

// Create user
val user = User(1, "John Doe", "john@email.com", "0700000000")

// Access properties
println(user.name)   // John Doe
println(user.email)  // john@email.com

// Copy with changes
val updatedUser = user.copy(name = "Jane Doe")

// Destructuring
val (id, name, email, phone) = user
```

### 10. Companion Object (Static)

```kotlin
class Constants {
    companion object {
        const val DB_NAME = "summit_coaches.db"
        const val DB_VERSION = 1
    }
}

// Use it
val dbName = Constants.DB_NAME
```

## 🎓 Key Concepts for Android

### 1. Activity Basics

```kotlin
class LoginActivity : AppCompatActivity() {
    
    // Called when activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)  // Set UI
        
        // Your code here
        setupUI()
    }
    
    fun setupUI() {
        // Initialize UI elements
    }
}
```

### 2. Finding Views

```kotlin
// In XML layout (activity_login.xml):
// <EditText android:id="@+id/editEmail" />

// In Kotlin:
val editEmail = findViewById<EditText>(R.id.editEmail)
val btnLogin = findViewById<Button>(R.id.btnLogin)

// Get text from EditText
val email = editEmail.text.toString()

// Set text
editEmail.setText("test@email.com")
```

### 3. Button Click

```kotlin
val btnLogin = findViewById<Button>(R.id.btnLogin)

// Set click listener
btnLogin.setOnClickListener {
    // Code when button clicked
    val email = editEmail.text.toString()
    performLogin(email)
}
```

### 4. Toast Messages

```kotlin
// Short message
Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

// Long message
Toast.makeText(this, "Error: Invalid email", Toast.LENGTH_LONG).show()
```

### 5. Intents (Navigation)

```kotlin
// Go to another activity
val intent = Intent(this, DashboardActivity::class.java)
startActivity(intent)

// Pass data
val intent = Intent(this, DashboardActivity::class.java)
intent.putExtra("USER_ID", 123)
intent.putExtra("USER_NAME", "John")
startActivity(intent)

// Receive data in DashboardActivity
val userId = intent.getLongExtra("USER_ID", 0)
val userName = intent.getStringExtra("USER_NAME")
```

### 6. SharedPreferences (Save Simple Data)

```kotlin
// Save data
val prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
prefs.edit().apply {
    putString("email", "john@email.com")
    putInt("user_id", 123)
    putBoolean("is_logged_in", true)
    apply()
}

// Read data
val email = prefs.getString("email", "")
val userId = prefs.getInt("user_id", 0)
val isLoggedIn = prefs.getBoolean("is_logged_in", false)
```

## 🗄️ Database Concepts

### 1. ContentValues (Insert Data)

```kotlin
val values = ContentValues().apply {
    put("name", "John Doe")
    put("email", "john@email.com")
    put("phone", "0700000000")
}

val id = db.insert("users", null, values)
```

### 2. Cursor (Read Data)

```kotlin
val cursor = db.query(
    "users",        // Table name
    null,           // Columns (null = all)
    "id=?",         // Where clause
    arrayOf("1"),   // Where arguments
    null, null, null
)

// Read data
if (cursor.moveToFirst()) {
    val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
    val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
}
cursor.close()
```

### 3. Update Data

```kotlin
val values = ContentValues().apply {
    put("name", "Jane Doe")
    put("email", "jane@email.com")
}

val rows = db.update(
    "users",           // Table
    values,            // New values
    "id=?",            // Where
    arrayOf("1")       // Arguments
)
```

### 4. Delete Data

```kotlin
val rows = db.delete(
    "users",           // Table
    "id=?",            // Where
    arrayOf("1")       // Arguments
)
```

## 📝 Common Patterns in Our App

### 1. Input Validation

```kotlin
fun validateEmail(email: String): Boolean {
    return email.isNotEmpty() && 
           email.contains("@") && 
           email.contains(".")
}

fun validatePassword(password: String): Boolean {
    return password.length >= 6
}

// Use it
val email = editEmail.text.toString()
if (!validateEmail(email)) {
    Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show()
    return
}
```

### 2. Try-Catch (Error Handling)

```kotlin
try {
    // Code that might fail
    val result = database.insertUser(user)
    Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
} catch (e: Exception) {
    // Handle error
    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
    Log.e("TAG", "Error inserting user", e)
}
```

### 3. RecyclerView Adapter

```kotlin
class TripAdapter(private val trips: List<Trip>) : 
    RecyclerView.Adapter<TripAdapter.ViewHolder>() {
    
    // ViewHolder holds views
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtRoute: TextView = view.findViewById(R.id.txtRoute)
        val txtTime: TextView = view.findViewById(R.id.txtTime)
        val txtPrice: TextView = view.findViewById(R.id.txtPrice)
    }
    
    // Create view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trip, parent, false)
        return ViewHolder(view)
    }
    
    // Bind data to view holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trip = trips[position]
        holder.txtRoute.text = "${trip.fromLocation} → ${trip.toLocation}"
        holder.txtTime.text = trip.departureTime
        holder.txtPrice.text = "UGX ${trip.price}"
    }
    
    // Return item count
    override fun getItemCount() = trips.size
}
```

## 🎯 Best Practices

### 1. Naming Conventions

```kotlin
// Classes: PascalCase
class LoginActivity
class DatabaseHelper
class TripAdapter

// Variables & functions: camelCase
val userName = "John"
fun validateEmail()
fun insertBooking()

// Constants: UPPER_SNAKE_CASE
const val MAX_SEATS = 50
const val DB_NAME = "summit.db"
```

### 2. Code Organization

```kotlin
class LoginActivity : AppCompatActivity() {
    
    // 1. Properties at top
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var dbHelper: DatabaseHelper
    
    // 2. Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        initializeViews()
        setupClickListeners()
    }
    
    // 3. Setup methods
    private fun initializeViews() {
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        btnLogin = findViewById(R.id.btnLogin)
        dbHelper = DatabaseHelper(this)
    }
    
    private fun setupClickListeners() {
        btnLogin.setOnClickListener {
            performLogin()
        }
    }
    
    // 4. Business logic
    private fun performLogin() {
        // Login logic here
    }
    
    // 5. Helper methods
    private fun validateInput(): Boolean {
        // Validation logic
        return true
    }
}
```

### 3. Comments

```kotlin
// Single line comment

/*
 * Multi-line comment
 * Explains complex logic
 */

/**
 * Documentation comment
 * Describes what function does
 *
 * @param email User's email address
 * @param password User's password
 * @return true if login successful
 */
fun login(email: String, password: String): Boolean {
    // Implementation
    return true
}
```

## 🚀 Quick Reference

### Common Kotlin Keywords

- `val` - Read-only variable
- `var` - Mutable variable
- `fun` - Function
- `class` - Class definition
- `object` - Singleton
- `data class` - Data holder
- `if` - Condition
- `when` - Switch statement
- `for` - Loop
- `while` - Loop while condition true
- `return` - Return value
- `null` - No value
- `true/false` - Boolean values

### Common Android Classes

- `AppCompatActivity` - Base class for activities
- `Intent` - Navigate between activities
- `Bundle` - Pass data between activities
- `Toast` - Show short message
- `Log` - Debug logging
- `SharedPreferences` - Save simple data
- `SQLiteDatabase` - Database operations
- `RecyclerView` - Scrollable lists
- `Adapter` - Provides data to RecyclerView

## 📚 Learning Tips

1. **Start Small**: Don't try to understand everything at once
2. **Read Code**: Look at existing code in the app
3. **Experiment**: Change values and see what happens
4. **Debug**: Use `Log.d()` to print values
5. **Ask Questions**: Search error messages on Google
6. **Practice**: Modify the app to add features

## 🎓 Next Steps

1. Read this guide completely
2. Open **SplashActivity.kt** and understand it
3. Look at **activity_splash.xml** layout
4. See how Activity and Layout connect
5. Read **DatabaseHelper.kt** to understand database
6. Explore other activities one by one

## 🔗 Helpful Resources

- [Kotlin Playground](https://play.kotlinlang.org/) - Try Kotlin online
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Android Developer Guides](https://developer.android.com/guide)

---

**You're ready to start! Open the project and explore!** 🚀
