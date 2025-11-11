package com.summitcoaches.app.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.summitcoaches.app.R
import com.summitcoaches.app.database.DatabaseHelper
import com.summitcoaches.app.models.User
import com.summitcoaches.app.utils.SessionManager
import com.summitcoaches.app.utils.ValidationUtils

class AccountDetailsActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var btnUpdate: Button
    
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager
    private var currentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_details)

        supportActionBar?.title = "Account Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = DatabaseHelper(this)
        sessionManager = SessionManager(this)

        initializeViews()
        loadUserDetails()
        setupListeners()
    }

    private fun initializeViews() {
        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        btnUpdate = findViewById(R.id.btnUpdate)
    }

    private fun loadUserDetails() {
        currentUser = dbHelper.getUserById(sessionManager.getUserId())
        currentUser?.let {
            etFullName.setText(it.fullName)
            etEmail.setText(it.email)
            etPhone.setText(it.phone)
        }
    }

    private fun setupListeners() {
        btnUpdate.setOnClickListener {
            updateUserDetails()
        }
    }

    private fun updateUserDetails() {
        val fullName = etFullName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val phone = etPhone.text.toString().trim()

        if (!validateInputs(fullName, email, phone)) return

        currentUser?.let { user ->
            val updatedUser = user.copy(
                fullName = fullName,
                email = email,
                phone = phone
            )

            val result = dbHelper.updateUser(updatedUser)
            if (result > 0) {
                sessionManager.saveSession(user.id, fullName, email, user.role)
                Toast.makeText(this, "Account updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to update account", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInputs(name: String, email: String, phone: String): Boolean {
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

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
