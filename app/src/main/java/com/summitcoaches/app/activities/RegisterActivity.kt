package com.summitcoaches.app.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.summitcoaches.app.R
import com.summitcoaches.app.database.DatabaseHelper
import com.summitcoaches.app.models.User
import com.summitcoaches.app.utils.ValidationUtils

class RegisterActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvLogin: TextView
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initializeViews()
        dbHelper = DatabaseHelper(this)
        setupListeners()
    }

    private fun initializeViews() {
        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvLogin = findViewById(R.id.tvLogin)
    }

    private fun setupListeners() {
        btnRegister.setOnClickListener {
            performRegistration()
        }

        tvLogin.setOnClickListener {
            finish()
        }
    }

    private fun performRegistration() {
        val fullName = etFullName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val confirmPassword = etConfirmPassword.text.toString().trim()

        if (!validateInputs(fullName, email, phone, password, confirmPassword)) {
            return
        }

        if (dbHelper.emailExists(email)) {
            etEmail.error = "Email already registered"
            return
        }

        val user = User(
            fullName = fullName,
            email = email,
            phone = phone,
            password = password,
            role = "Clerk"
        )

        val result = dbHelper.createUser(user)

        if (result > 0) {
            Toast.makeText(this, "Registration successful! Please login.", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInputs(name: String, email: String, phone: String, password: String, confirmPassword: String): Boolean {
        if (!ValidationUtils.isNotEmpty(name)) {
            etFullName.error = ValidationUtils.getRequiredFieldError()
            return false
        }

        if (!ValidationUtils.isValidEmail(email)) {
            etEmail.error = ValidationUtils.getEmailError()
            return false
        }

        if (!ValidationUtils.isValidPhone(phone)) {
            etPhone.error = ValidationUtils.getPhoneError()
            return false
        }

        if (!ValidationUtils.isValidPassword(password)) {
            etPassword.error = ValidationUtils.getPasswordError()
            return false
        }

        if (password != confirmPassword) {
            etConfirmPassword.error = "Passwords do not match"
            return false
        }

        return true
    }
}
