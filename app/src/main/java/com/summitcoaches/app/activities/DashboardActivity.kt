import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.summitcoaches.app.activities.AboutActivity
import com.summitcoaches.app.R
import com.summitcoaches.app.activities.AccountDetailsActivity
import com.summitcoaches.app.activities.BookingHistoryActivity
import com.summitcoaches.app.activities.SelectTripActivity

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

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        // Handle drawer item clicks
        navView.setNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.nav_dashboard -> {
                    // Load Dashboard fragment
                    true
                }
                R.id.nav_about -> {
                    // Show About fragment or activity
                    startActivity(Intent(this, AboutActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // Handle bottom navigation clicks
        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId){
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
}
