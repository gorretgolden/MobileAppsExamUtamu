package com.summitcoaches.app.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * SessionManager - Manages user session and login state
 * Uses SharedPreferences to persist user data across app launches
 */
class SessionManager(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(
        Constants.PREF_NAME,
        Context.MODE_PRIVATE
    )

    /**
     * Save user session after login
     */
    fun saveSession(userId: Int, userName: String, userEmail: String, userRole: String) {
        prefs.edit().apply {
            putInt(Constants.KEY_USER_ID, userId)
            putString(Constants.KEY_USER_NAME, userName)
            putString(Constants.KEY_USER_EMAIL, userEmail)
            putString(Constants.KEY_USER_ROLE, userRole)
            putBoolean(Constants.KEY_IS_LOGGED_IN, true)
            apply()
        }
    }

    /**
     * Clear user session (logout)
     */
    fun clearSession() {
        prefs.edit().apply {
            clear()
            apply()
        }
    }

    /**
     * Check if user is logged in
     */
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(Constants.KEY_IS_LOGGED_IN, false)
    }

    /**
     * Get logged in user ID
     */
    fun getUserId(): Int {
        return prefs.getInt(Constants.KEY_USER_ID, 0)
    }

    /**
     * Get logged in user name
     */
    fun getUserName(): String {
        return prefs.getString(Constants.KEY_USER_NAME, "") ?: ""
    }

    /**
     * Get logged in user email
     */
    fun getUserEmail(): String {
        return prefs.getString(Constants.KEY_USER_EMAIL, "") ?: ""
    }

    /**
     * Get logged in user role
     */
    fun getUserRole(): String {
        return prefs.getString(Constants.KEY_USER_ROLE, "Clerk") ?: "Clerk"
    }

    /**
     * Set first time launch flag
     */
    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        prefs.edit().putBoolean(Constants.KEY_FIRST_TIME, isFirstTime).apply()
    }

    /**
     * Check if this is first time launch
     */
    fun isFirstTimeLaunch(): Boolean {
        return prefs.getBoolean(Constants.KEY_FIRST_TIME, true)
    }
}
