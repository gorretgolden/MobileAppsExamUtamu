package com.summitcoaches.app.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.summitcoaches.app.R
import com.summitcoaches.app.utils.ValidationUtils

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var btnReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        etEmail = findViewById(R.id.etEmail)
        btnReset = findViewById(R.id.btnReset)

        btnReset.setOnClickListener {
            val email = etEmail.text.toString().trim()
            if (ValidationUtils.isValidEmail(email)) {
                Toast.makeText(this, "Password reset link sent to $email", Toast.LENGTH_LONG).show()
                finish()
            } else {
                etEmail.error = ValidationUtils.getEmailError()
            }
        }
    }
}
