package com.comp3074_101384549.projectui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.comp3074_101384549.projectui.data.local.AuthPreferences
import com.comp3074_101384549.projectui.databinding.ActivityHomeBinding
import com.comp3074_101384549.projectui.ui.home.HomeFragment
import com.comp3074_101384549.projectui.ui.listings.CreateListingFragment
import com.comp3074_101384549.projectui.ui.listings.MyListingsFragment
import com.comp3074_101384549.projectui.ui.payment.PaymentFragment
import com.comp3074_101384549.projectui.ui.profile.ProfileFragment
import com.comp3074_101384549.projectui.ui.reservations.ReservedListingsFragment
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var authPreferences: AuthPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Auth preferences
        authPreferences = AuthPreferences(applicationContext)

        // Set up bottom navigation + drawer menu
        setupBottomNav()
        setupDrawerMenu()

        // Show Home fragment the first time
        if (savedInstanceState == null) {
            openFragment(HomeFragment())
            binding.bottomNav.selectedItemId = R.id.homeFragment
        }
    }

    private fun setupBottomNav() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    openFragment(HomeFragment())
                    true
                }
                R.id.addFragment -> {
                    // Use the same Create Listing fragment as the drawer item
                    openFragment(CreateListingFragment())
                    true
                }
                R.id.drawerMenu -> {
                    binding.drawerLayout.openDrawer(GravityCompat.END)
                    true
                }
                else -> false
            }
        }
    }

        // Handle side drawer menu clicks
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
    private fun setupDrawerMenu() {
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                // Profile
                R.id.nav_profile -> {
                    openFragment(ProfileFragment())
                    binding.drawerLayout.closeDrawers()
                    true
                }

                // "Create Listing"
                R.id.nav_listings_created -> {
                    openFragment(CreateListingFragment())
                    binding.drawerLayout.closeDrawers()
                    true
                }

                // "Listings reserved"
                R.id.nav_listings_reserved -> {
                    openFragment(ReservedListingsFragment())
                    binding.drawerLayout.closeDrawers()
                    true
                }

                // "MyListings"
                R.id.nav_my_listings -> {
                    openFragment(MyListingsFragment())
                    binding.drawerLayout.closeDrawers()
                    true
                }

                // "Payment methods"
                R.id.nav_payment_methods -> {
                    openFragment(PaymentFragment())
                    binding.drawerLayout.closeDrawers()
                    true
                }

                // Logout
                R.id.nav_logout -> {
                    performLogout()
                    binding.drawerLayout.closeDrawers()
                    true
                }

                // "Support / help" – placeholder
                R.id.nav_help -> {
                    Toast.makeText(
                        this,
                        "Support / help screen not implemented yet (prototype).",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.drawerLayout.closeDrawers()
                    true
                }

                else -> false
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeFragmentContainer, fragment)
            .commit()
    }

    /**
     * Clears the user session and redirects to the splash screen (MainActivity).
     */
    private fun performLogout() {
        lifecycleScope.launch {
            // Clear stored auth details
            authPreferences.clearAuthDetails()

            // Go back to the splash / main screen
            val intent = Intent(this@HomeActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
