package com.summitcoaches.app.activities


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.summitcoaches.app.R

class SplashActivity : AppCompatActivity() {
    private val splashDuration = 2000L // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, AboutActivity::class.java))
            finish()
        }, splashDuration)
    }
}
