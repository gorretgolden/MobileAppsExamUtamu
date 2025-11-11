# 🛠️ Android Studio Setup Guide

## Step-by-Step Installation

### 1. Download Android Studio

1. Go to: **https://developer.android.com/studio**
2. Click the green **Download Android Studio** button
3. Accept the terms and conditions
4. Download will start (around 1GB)

### 2. Install Android Studio

#### Windows:
1. Run the downloaded `.exe` file
2. Click **Next** through the installer
3. Choose **Standard** installation
4. Wait for Android SDK to download (can take 20-30 minutes)
5. Click **Finish**

#### Mac:
1. Open the downloaded `.dmg` file
2. Drag Android Studio to Applications folder
3. Open Android Studio from Applications
4. Follow the setup wizard
5. Choose **Standard** installation

### 3. First Launch Setup

When you open Android Studio for the first time:

1. **Welcome Screen** appears
2. Click **More Actions** → **SDK Manager**
3. Make sure these are installed:
   - ✅ Android SDK Platform 33
   - ✅ Android SDK Build-Tools
   - ✅ Android Emulator
   - ✅ Android SDK Platform-Tools
4. Click **OK** to download any missing components

### 4. Open the Summit Coaches Project

1. Extract the `SummitCoaches.zip` file
2. Open Android Studio
3. Click **File** → **Open**
4. Navigate to the **SummitCoaches** folder
5. Click **OK**
6. Wait for Gradle sync (5-10 minutes first time)
7. You'll see "BUILD SUCCESSFUL" at the bottom

### 5. Create an Emulator (Virtual Phone)

1. Click **Device Manager** icon on the right side
2. Click **Create Device**
3. Select **Phone** category
4. Choose **Pixel 5** or **Pixel 6**
5. Click **Next**
6. Select **Tiramisu** (API Level 33) or latest available
7. Click **Next**
8. Give it a name (e.g., "Test Phone")
9. Click **Finish**
10. Wait for the system image to download

### 6. Run the App

1. Click the **green play button** ▶️ at the top toolbar
2. Select your emulator from the dropdown
3. Click **OK**
4. Wait for emulator to boot (2-3 minutes first time)
5. App will install and launch automatically
6. You should see the Summit Coaches splash screen!

## Common Setup Issues

### Issue 1: "SDK Not Found"

**Solution:**
1. Open **File** → **Project Structure**
2. Click **SDK Location**
3. Set Android SDK Location to:
   - Windows: `C:\Users\YourName\AppData\Local\Android\Sdk`
   - Mac: `/Users/YourName/Library/Android/sdk`
4. Click **OK**

### Issue 2: Gradle Sync Failed

**Solution 1:**
1. Click **File** → **Invalidate Caches**
2. Check **Invalidate and Restart**
3. Wait for Android Studio to restart

**Solution 2:**
1. Check your internet connection
2. Try again (Gradle might be downloading dependencies)

### Issue 3: Emulator Won't Start

**Solution:**
1. Check if Intel HAXM or AMD Virtualization is enabled in BIOS
2. Windows: Enable Hyper-V in Windows Features
3. Mac: Enable System Integrity Protection
4. Restart computer

### Issue 4: "Unable to locate ADB"

**Solution:**
1. Open **SDK Manager**
2. Go to **SDK Tools** tab
3. Check **Android SDK Platform-Tools**
4. Click **OK** to install
5. Restart Android Studio

## Understanding the Interface

### Main Toolbar
- ▶️ **Run App** - Build and run on emulator/device
- 🔨 **Make Project** - Compile code
- 🛑 **Stop** - Stop running app
- 📱 **Device Manager** - Manage emulators

### Left Panel - Project Structure
```
app/
  ├── manifests/
  │   └── AndroidManifest.xml
  ├── java/
  │   └── com.summitcoaches.app/
  │       ├── activities/
  │       ├── models/
  │       ├── database/
  │       ├── adapters/
  │       └── utils/
  └── res/
      ├── drawable/
      ├── layout/
      ├── mipmap/
      └── values/
```

### Bottom Panel
- **Logcat** - View app logs and errors
- **Build** - Build output and errors
- **Terminal** - Command line
- **TODO** - Code TODO comments

### Right Panel
- **Gradle** - Build tasks
- **Device Manager** - Emulator management

## Using the Emulator

### Basic Controls
- **Power** - Top right button (long press for menu)
- **Volume** - Side buttons
- **Back** - Triangle button at bottom
- **Home** - Circle button at bottom
- **Recent Apps** - Square button at bottom

### Useful Shortcuts
- **Ctrl + M** (Windows) / **Cmd + M** (Mac) - Toggle menu
- **Ctrl + R** (Windows) / **Cmd + R** (Mac) - Rotate screen
- Take screenshot - Click camera icon

### Emulator Settings
1. Click **...** (three dots) on emulator sidebar
2. Access:
   - Settings
   - Location (GPS)
   - Cellular
   - Battery
   - Phone
   - Display settings

## Building APK for Real Phone

### Debug APK (For Testing)
1. Click **Build** → **Build Bundle(s) / APK(s)**
2. Click **Build APK(s)**
3. Wait for build
4. Click **locate** in notification
5. Transfer APK to phone
6. Install and test

### Installing on Real Phone
1. Enable **Developer Options** on phone:
   - Go to **Settings** → **About Phone**
   - Tap **Build Number** 7 times
   - Go back to **Settings**
   - Open **Developer Options**
   - Enable **USB Debugging**
2. Connect phone with USB cable
3. Allow USB debugging on phone
4. Phone appears in device dropdown
5. Click **Run** ▶️
6. App installs on phone

## Gradle Basics

### What is Gradle?
Gradle is the build system that:
- Compiles your Kotlin code
- Packages resources
- Creates the APK file
- Manages dependencies

### build.gradle Files

**Project-level** (`build.gradle`):
- Plugin versions
- Repository sources

**App-level** (`app/build.gradle`):
- App configuration
- Dependencies
- Minimum SDK version
- Target SDK version

### Common Gradle Tasks
```
./gradlew clean      - Clean build files
./gradlew build      - Build the app
./gradlew assembleDebug - Build debug APK
```

## Keyboard Shortcuts

### Essential Shortcuts

#### Windows:
- **Ctrl + Space** - Code completion
- **Ctrl + /** - Comment line
- **Ctrl + D** - Duplicate line
- **Ctrl + F** - Find in file
- **Shift + Shift** - Search everywhere
- **Alt + Enter** - Quick fix
- **Ctrl + Alt + L** - Format code

#### Mac:
- **Cmd + Space** - Code completion
- **Cmd + /** - Comment line
- **Cmd + D** - Duplicate line
- **Cmd + F** - Find in file
- **Shift + Shift** - Search everywhere
- **Option + Enter** - Quick fix
- **Cmd + Option + L** - Format code

## Logcat Usage

### View Logs
1. Run the app
2. Click **Logcat** at bottom
3. Look for your package: `com.summitcoaches.app`

### Filter Logs
```
tag:MyTag          - Filter by tag
package:mine       - Show only your app
level:error        - Show only errors
```

### Log Levels
- **Verbose** (V) - Detailed info
- **Debug** (D) - Debug messages
- **Info** (I) - General info
- **Warn** (W) - Warnings
- **Error** (E) - Errors
- **Assert** (A) - Critical errors

### Adding Logs in Code
```kotlin
import android.util.Log

Log.d("MyTag", "Debug message")
Log.e("MyTag", "Error message")
Log.i("MyTag", "Info message")
```

## Debugging

### Breakpoints
1. Click in the gutter (left of line number)
2. Red dot appears
3. Run app in **Debug mode** (bug icon)
4. App pauses at breakpoint
5. Inspect variables in Debug panel

### Debug Controls
- **Step Over** (F8) - Next line
- **Step Into** (F7) - Go inside function
- **Resume** (F9) - Continue execution

## Tips for Beginners

### 1. Auto Import
When you see red underlined code:
- Press **Alt + Enter** (Windows)
- Press **Option + Enter** (Mac)
- Select **Import**

### 2. Code Formatting
Format messy code:
- **Ctrl + Alt + L** (Windows)
- **Cmd + Option + L** (Mac)

### 3. Find Anything
Search for files, classes, actions:
- Press **Shift + Shift**
- Type what you're looking for

### 4. Refactor (Rename)
Rename variable everywhere:
- Right-click variable
- **Refactor** → **Rename**
- Type new name
- Press **Enter**

### 5. Quick Documentation
See documentation for function:
- Put cursor on function
- Press **Ctrl + Q** (Windows)
- Press **F1** (Mac)

## Updating Dependencies

### Check for Updates
1. **File** → **Project Structure**
2. **Suggestions** tab
3. Click **Update** for outdated dependencies

### Sync Project
After changing `build.gradle`:
- Click **Sync Now** notification
- Or **File** → **Sync Project with Gradle Files**

## Performance Tips

### Speed Up Android Studio

1. **Increase Memory:**
   - **Help** → **Edit Custom VM Options**
   - Change `-Xmx` to at least 4096m
   
2. **Enable Offline Mode:**
   - **File** → **Settings** → **Gradle**
   - Check **Offline work**

3. **Disable Unused Plugins:**
   - **File** → **Settings** → **Plugins**
   - Disable plugins you don't use

## Resources

### Official Documentation
- Android Developer Guide: https://developer.android.com/guide
- Kotlin Documentation: https://kotlinlang.org/docs/home.html

### Learning Platforms
- Android Basics Course: https://developer.android.com/courses
- Kotlin Playground: https://play.kotlinlang.org/

### Community
- Stack Overflow: https://stackoverflow.com/questions/tagged/android
- Reddit: r/androiddev

## Next Steps

1. ✅ Install Android Studio
2. ✅ Open Summit Coaches project
3. ✅ Create emulator
4. ✅ Run the app
5. 📖 Read KOTLIN_BASICS.md
6. 📖 Read DATABASE_GUIDE.md
7. 📖 Read PROJECT_WALKTHROUGH.md
8. 🚀 Start coding!

---

**You're all set! Happy coding!** 🎉
