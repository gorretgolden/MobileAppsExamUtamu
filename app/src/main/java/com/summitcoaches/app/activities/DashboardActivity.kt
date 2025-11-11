package com.summitcoaches.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import com.summitcoaches.app.R

class DashboardActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        bottomNav = findViewById(R.id.bottom_navigation)
        toolbar = findViewById(R.id.toolbar)

        // Setup toolbar
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Static user name and role in drawer header
        val headerView: View = navView.getHeaderView(0)
        val tvDeveloperName: TextView = headerView.findViewById(R.id.tvDeveloperName)
        val tvDeveloperEmail: TextView = headerView.findViewById(R.id.tvDeveloperEmail)
        val tvDeveloperContact: TextView = headerView.findViewById(R.id.tvDeveloperContact)

        tvDeveloperName.text = "Developer: Nabatanzi Gorret"
        tvDeveloperContact.text = "Contact: 0751547654"
        // Load Home fragment by default
        loadFragment(HomeFragment())

        // Handle drawer menu clicks
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    loadFragment(HomeFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_contact -> {
                  //  startActivity(Intent(this, DeveloperInfoActivity::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }

        // Handle bottom navigation clicks
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.bottom_create_booking -> {
                    startActivity(Intent(this, SelectTripActivity::class.java))
                    true
                }
                R.id.bottom_history -> {
                    startActivity(Intent(this, BookingHistoryActivity::class.java))
                    true
                }
                R.id.bottom_account -> {
                    startActivity(Intent(this, AccountDetailsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
