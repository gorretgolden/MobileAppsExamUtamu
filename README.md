# 🚌 Summit Coaches - Bus Booking Android App

![Summit Coaches Logo](app/src/main/res/mipmap-xxxhdpi/ic_launcher.png)

## 📱 About The App

Summit Coaches is a complete Android bus booking application built with **Kotlin** that allows booking clerks to:
- Create bookings for passengers, luggage, and parcels
- View booking history
- Manage account details
- Access trip schedules

### ✨ Key Features

- 🎨 **Beautiful Blue Theme** - Professional Material Design UI
- 📱 **Easy Navigation** - Intuitive user interface
- 🔐 **Secure Authentication** - Login, Register, Forgot Password
- 📊 **Booking Management** - Create and track bookings
- 💾 **Offline Support** - SQLite database works without internet
- 🚀 **Optimized Performance** - Small app size, fast loading

## 📋 Requirements

- **Android Studio** Giraffe | 2022.3.1 or newer
- **Minimum SDK**: Android 5.0 (API 21)
- **Target SDK**: Android 13 (API 33)
- **Kotlin**: 1.9.0

## 🚀 Quick Start Guide

### Step 1: Install Android Studio

1. Download from: https://developer.android.com/studio
2. Install Android Studio
3. During installation, install Android SDK

### Step 2: Open The Project

1. Extract the **SummitCoaches** folder
2. Open Android Studio
3. Click **File → Open**
4. Navigate to **SummitCoaches** folder
5. Click **OK**

### Step 3: Wait for Gradle Sync

- Android Studio will automatically download dependencies
- This may take 5-10 minutes (first time only)
- Wait until you see "BUILD SUCCESSFUL" at the bottom

### Step 4: Run The App

**Option A: Using Emulator (Recommended for beginners)**
1. Click **Device Manager** (phone icon on right side)
2. Click **Create Device**
3. Select **Pixel 5** or any phone
4. Select **API 33** (Tiramisu)
5. Click **Next** → **Finish**
6. Click the **green play button** ▶️ at the top
7. Select your emulator
8. Click **OK**

**Option B: Using Real Phone**
1. Enable **Developer Options** on your phone:
   - Go to **Settings → About Phone**
   - Tap **Build Number** 7 times
   - Go back to **Settings → Developer Options**
   - Enable **USB Debugging**
2. Connect phone to computer with USB cable
3. Allow USB Debugging on phone
4. Click the **green play button** ▶️
5. Select your phone
6. Click **OK**

## 🔐 Default Login Credentials

The app comes with pre-loaded test accounts:

**Booking Clerk Account:**
- Email: `clerk@summitcoaches.com`
- Password: `clerk123`

**Test Account:**
- Email: `john@email.com`
- Password: `password123`

**Note:** You can also register a new account!

## 📂 Project Structure

```
SummitCoaches/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/summitcoaches/app/
│   │       │   ├── activities/          # All app screens
│   │       │   │   ├── SplashActivity.kt
│   │       │   │   ├── OnboardingActivity.kt
│   │       │   │   ├── LoginActivity.kt
│   │       │   │   ├── RegisterActivity.kt
│   │       │   │   ├── DashboardActivity.kt
│   │       │   │   └── ...
│   │       │   │
│   │       │   ├── models/              # Data classes
│   │       │   │   └── Models.kt
│   │       │   │
│   │       │   ├── database/            # Database logic
│   │       │   │   ├── DatabaseHelper.kt
│   │       │   │   └── DBConstants.kt
│   │       │   │
│   │       │   ├── adapters/            # RecyclerView adapters
│   │       │   │   ├── TripAdapter.kt
│   │       │   │   ├── BookingAdapter.kt
│   │       │   │   └── OnboardingAdapter.kt
│   │       │   │
│   │       │   └── utils/               # Helper utilities
│   │       │       ├── Constants.kt
│   │       │       ├── SessionManager.kt
│   │       │       └── ValidationUtils.kt
│   │       │
│   │       ├── res/
│   │       │   ├── layout/              # XML layouts
│   │       │   ├── drawable/            # Images & shapes
│   │       │   ├── values/              # Colors, strings, themes
│   │       │   └── mipmap/              # App icons
│   │       │
│   │       └── AndroidManifest.xml      # App configuration
│   │
│   └── build.gradle                     # App dependencies
│
├── build.gradle                         # Project configuration
├── settings.gradle                      # Project settings
└── README.md                            # This file
```

## 🎓 Learning Resources

### For Complete Beginners

Read these documents in order:
1. **KOTLIN_BASICS.md** - Learn Kotlin fundamentals
2. **ANDROID_BASICS.md** - Understand Android development
3. **DATABASE_GUIDE.md** - Learn about SQLite
4. **PROJECT_WALKTHROUGH.md** - Detailed code explanation

### Key Concepts You'll Learn

- **Activities**: Each screen in Android
- **Layouts**: XML files that define UI
- **Intents**: Navigation between screens
- **SQLite**: Local database on the phone
- **RecyclerView**: Scrollable lists
- **Material Design**: Beautiful UI components

## 🗄️ Database Information

### What is SQLite?

- **SQLite** is a lightweight database built into Android
- No server needed (unlike MySQL)
- Data stored locally on the phone
- Works offline
- Perfect for mobile apps

### Why Not MySQL?

MySQL requires:
- A server running 24/7
- Internet connection always
- Backend API (PHP/Node.js)
- Hosting costs

SQLite is:
- ✅ Built into Android
- ✅ No internet needed
- ✅ Free
- ✅ Fast
- ✅ Perfect for mobile

### Database Tables

The app has 8 tables:
1. **users** - Booking clerk accounts
2. **bus_types** - Types of buses (e.g., VIP, Regular)
3. **buses** - Individual buses
4. **routes** - Travel routes (e.g., Kampala → Mbarara)
5. **trips** - Scheduled trips with date/time
6. **bookings** - Customer bookings
7. **payments** - Payment records
8. **booking_types** - Passenger, Luggage, Parcel

### Pre-loaded Data

The app includes sample data:
- 2 test users (clerks)
- 3 bus types (VIP, Regular, Economy)
- 5 buses
- 4 routes (Kampala to various cities)
- 10 scheduled trips
- Sample booking types

## 🎨 UI Design

### Color Theme

- **Primary Blue**: `#1976D2`
- **Dark Blue**: `#0D47A1`
- **Light Background**: `#F5F5F5`
- **Text**: `#212121`
- **Secondary Text**: `#757575`

### Screens

1. **Splash Screen** - Logo and app name
2. **Onboarding** - 3 intro screens (swipeable)
3. **Login** - Email and password
4. **Register** - Create new account
5. **Forgot Password** - Reset password
6. **Dashboard** - Main menu (booking cards)
7. **Select Trip** - Choose from available trips
8. **Create Booking** - Fill booking details
9. **Booking History** - View past bookings
10. **Account Details** - Edit profile

## 🔧 Common Issues & Solutions

### Issue 1: Gradle Sync Failed

**Solution:**
1. Click **File → Invalidate Caches**
2. Check **Invalidate and Restart**
3. Wait for Android Studio to restart

### Issue 2: App Won't Run

**Solution:**
1. Clean project: **Build → Clean Project**
2. Rebuild: **Build → Rebuild Project**
3. Try running again

### Issue 3: Emulator is Slow

**Solution:**
1. Close other programs
2. In Android Studio: **Tools → AVD Manager**
3. Edit your emulator
4. Increase **RAM** to 2048 MB
5. Enable **Hardware Acceleration**

### Issue 4: Cannot Find Symbol Error

**Solution:**
1. Check imports at top of .kt files
2. **Build → Clean Project**
3. **Build → Rebuild Project**

### Issue 5: Database Error

**Solution:**
1. Uninstall app from emulator/phone
2. Reinstall (this recreates database)

## 📱 How to Use the App

### For Booking Clerks

1. **Login** with your credentials
2. **Dashboard** shows main options:
   - Create Booking
   - Booking History
   - Account Details
3. **Create Booking:**
   - Select booking type (Passenger/Luggage/Parcel)
   - Choose a trip
   - Fill customer details
   - Confirm booking
4. **View History:**
   - See all your bookings
   - Check booking status
   - View details

## 🎯 Key Kotlin Concepts Used

### 1. Classes & Objects
```kotlin
// Data class - holds data
data class User(
    val id: Long,
    val name: String,
    val email: String
)

// Create object
val user = User(1, "John", "john@email.com")
```

### 2. Activities
```kotlin
// Each screen is an Activity
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}
```

### 3. Intents (Navigation)
```kotlin
// Go to another screen
val intent = Intent(this, DashboardActivity::class.java)
startActivity(intent)
```

### 4. Database Operations
```kotlin
// Insert data
db.insert("users", null, contentValues)

// Query data
val cursor = db.query("users", null, null, null, null, null, null)

// Update data
db.update("users", contentValues, "id=?", arrayOf("1"))
```

## 📚 Additional Resources

### Learn Kotlin
- [Kotlin Official Docs](https://kotlinlang.org/docs/home.html)
- [Kotlin Basics for Android](https://developer.android.com/kotlin/first)

### Learn Android
- [Android Developer Guides](https://developer.android.com/guide)
- [Android Kotlin Codelabs](https://developer.android.com/courses)

### Learn SQLite
- [SQLite Tutorial](https://www.sqlitetutorial.net/)
- [Android SQLite Guide](https://developer.android.com/training/data-storage/sqlite)

## 🐛 Debugging Tips

### View Logs (Logcat)
1. Click **Logcat** tab at bottom
2. Look for errors (red text)
3. Search for your package name: `com.summitcoaches.app`

### Add Debug Logs
```kotlin
import android.util.Log

// Add this in your code
Log.d("MyTag", "Debug message here")
Log.e("MyTag", "Error message here")
```

## 📦 Building APK

### For Testing
1. Click **Build → Build Bundle(s) / APK(s)**
2. Click **Build APK(s)**
3. Wait for build to complete
4. Click **locate** to find APK file
5. Transfer to phone and install

### For Release
1. Click **Build → Generate Signed Bundle / APK**
2. Select **APK**
3. Create or select keystore
4. Fill passwords
5. Select **release** variant
6. Click **Finish**

## 🤝 Support

### Need Help?

1. Read the documentation files
2. Check **Common Issues** section above
3. Look at code comments
4. Search error messages on Google
5. Check Stack Overflow

### Exam Tips

- Understand the **project structure**
- Know what each **Activity** does
- Explain **database operations**
- Be able to modify **layouts**
- Understand **data flow**

## 📝 License

This project is created for educational purposes.

## 🙏 Credits

- **Developer**: Created for Summit Coaches
- **Design**: Material Design Guidelines
- **Icons**: Material Icons
- **Database**: SQLite

---

## 🎓 For Your Exam

### Key Points to Remember:

1. **Kotlin Basics**
   - Variables: `val` (read-only), `var` (mutable)
   - Data classes for models
   - Functions with `fun` keyword

2. **Android Components**
   - Activities: Screens in the app
   - Layouts: XML UI design
   - Intents: Navigation between screens

3. **Database**
   - SQLite for local storage
   - CRUD operations (Create, Read, Update, Delete)
   - DatabaseHelper class manages database

4. **Architecture**
   - Activities for UI logic
   - Database layer for data
   - Models for data structures
   - Adapters for lists

5. **Material Design**
   - CardViews for cards
   - RecyclerViews for lists
   - TextInputLayouts for inputs
   - Material buttons and colors

### What Makes This App Production-Ready:

✅ Proper project structure
✅ Material Design UI
✅ Input validation
✅ Error handling
✅ Session management
✅ Database relationships
✅ Optimized performance
✅ Clean code with comments
✅ Responsive layouts
✅ Consistent naming

---

**Happy Coding! 🚀**

Read **KOTLIN_BASICS.md** next to understand the language!
