package com.summitcoaches.app.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.summitcoaches.app.R
import com.summitcoaches.app.database.DatabaseHelper
import com.summitcoaches.app.utils.SessionManager
import com.summitcoaches.app.utils.ValidationUtils

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView
    private lateinit var tvForgotPassword: TextView
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initializeViews()
        dbHelper = DatabaseHelper(this)
        sessionManager = SessionManager(this)

        setupListeners()
    }

    private fun initializeViews() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)
        tvForgotPassword = findViewById(R.id.tvForgotPassword)
    }

    private fun setupListeners() {
        btnLogin.setOnClickListener {
            performLogin()
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    private fun performLogin() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (!validateInputs(email, password)) {
            return
        }

        val user = dbHelper.loginUser(email, password)

        if (user != null) {
            sessionManager.saveSession(user.id, user.fullName, user.email, user.role)
            Toast.makeText(this, "Welcome, \${user.fullName}!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        if (!ValidationUtils.isValidEmail(email)) {
            etEmail.error = ValidationUtils.getEmailError()
            return false
        }

        if (password.isEmpty()) {
            etPassword.error = ValidationUtils.getRequiredFieldError()
            return false
        }

        return true
    }
}
