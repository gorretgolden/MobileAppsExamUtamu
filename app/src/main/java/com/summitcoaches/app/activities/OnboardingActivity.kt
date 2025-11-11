package com.summitcoaches.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.summitcoaches.app.R
import com.summitcoaches.app.adapters.OnboardingAdapter

/**
 * OnboardingActivity - Shows onboarding screens to new users
 * Three screens: Easy Booking, Real-time Tracking, Secure Payments
 */
class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var btnNext: Button
    private lateinit var btnSkip: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        initializeViews()
        setupOnboarding()
    }

    private fun initializeViews() {
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        btnNext = findViewById(R.id.btnNext)
        btnSkip = findViewById(R.id.btnSkip)
    }

    private fun setupOnboarding() {
        val adapter = OnboardingAdapter(this)
        viewPager.adapter = adapter

        // Link tab layout with view pager
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        // Next button click
        btnNext.setOnClickListener {
            if (viewPager.currentItem < 2) {
                viewPager.currentItem += 1
            } else {
                navigateToLogin()
            }
        }

        // Skip button click
        btnSkip.setOnClickListener {
            navigateToLogin()
        }

        // Update button text on last page
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                btnNext.text = if (position == 2) "Get Started" else "Next"
                btnSkip.visibility = if (position == 2) View.GONE else View.VISIBLE
            }
        })
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
