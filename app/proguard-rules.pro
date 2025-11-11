# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep data classes
-keep class com.summitcoaches.app.models.** { *; }

# Keep database helper
-keep class com.summitcoaches.app.database.** { *; }

# Keep utils
-keep class com.summitcoaches.app.utils.** { *; }
