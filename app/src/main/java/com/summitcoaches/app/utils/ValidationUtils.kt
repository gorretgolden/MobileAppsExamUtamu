package com.summitcoaches.app.utils

import java.util.regex.Pattern

/**
 * ValidationUtils - Input validation helper functions
 * Contains methods to validate email, phone, password, etc.
 */
object ValidationUtils {

    /**
     * Validate email format
     */
    fun isValidEmail(email: String): Boolean {
        if (email.isEmpty()) return false
        val pattern = Pattern.compile(Constants.EMAIL_REGEX)
        return pattern.matcher(email).matches()
    }

    /**
     * Validate phone number format
     * Accepts 10-13 digit phone numbers
     */
    fun isValidPhone(phone: String): Boolean {
        if (phone.isEmpty()) return false
        val pattern = Pattern.compile(Constants.PHONE_REGEX)
        return pattern.matcher(phone).matches()
    }

    /**
     * Validate password strength
     * Must be at least 6 characters
     */
    fun isValidPassword(password: String): Boolean {
        return password.length >= Constants.MIN_PASSWORD_LENGTH
    }

    /**
     * Check if string is not empty
     */
    fun isNotEmpty(text: String): Boolean {
        return text.trim().isNotEmpty()
    }

    /**
     * Validate full name (at least 2 words)
     */
    fun isValidFullName(name: String): Boolean {
        val trimmed = name.trim()
        return trimmed.isNotEmpty() && trimmed.split(" ").size >= 2
    }

    /**
     * Get validation error message
     */
    fun getEmailError(): String {
        return "Please enter a valid email address"
    }

    fun getPhoneError(): String {
        return "Please enter a valid phone number (10-13 digits)"
    }

    fun getPasswordError(): String {
        return "Password must be at least ${Constants.MIN_PASSWORD_LENGTH} characters"
    }

    fun getRequiredFieldError(): String {
        return "This field is required"
    }

    fun getNameError(): String {
        return "Please enter your full name (first and last name)"
    }
}
