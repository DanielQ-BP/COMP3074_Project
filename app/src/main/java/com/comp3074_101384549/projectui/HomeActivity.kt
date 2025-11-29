package com.comp3074_101384549.projectui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.comp3074_101384549.projectui.databinding.ActivityHomeBinding
import com.comp3074_101384549.projectui.ui.home.HomeFragment
import com.comp3074_101384549.projectui.ui.listings.CreateListingFragment
import com.comp3074_101384549.projectui.ui.listings.MyListingsFragment
import com.comp3074_101384549.projectui.ui.payment.PaymentFragment
import com.comp3074_101384549.projectui.ui.profile.ProfileFragment
import com.comp3074_101384549.projectui.ui.reservations.ReservedListingsFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load default fragment on first open
        if (savedInstanceState == null) {
            openFragment(HomeFragment())
        }

        setupBottomNavigation()
        setupDrawerNavigation()
    }

    // ------------------------ Fragment Navigation Functions ------------------------

    private fun openFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeFragmentContainer, fragment)
            .commit()
    }

    private fun openFragmentWithBackstack(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeFragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    // ------------------------ Bottom Navigation ------------------------

    private fun setupBottomNavigation() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    openFragment(HomeFragment())
                    true
                }
                R.id.addFragment -> {
                    // If "Add" should show CreateListing screen
                    openFragmentWithBackstack(CreateListingFragment())
                    true
                }
                R.id.drawerMenu -> {
                    binding.drawerLayout.openDrawer(GravityCompat.END)
                    false
                }
                else -> false
            }
        }
    }

    // ------------------------ Drawer Navigation ------------------------

    private fun setupDrawerNavigation() {
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.nav_profile ->
                    openFragmentWithBackstack(ProfileFragment())

                R.id.nav_listings_created ->
                    openFragmentWithBackstack(CreateListingFragment())

                R.id.nav_listings_reserved ->
                    openFragmentWithBackstack(ReservedListingsFragment())

                R.id.nav_my_listings ->
                    openFragmentWithBackstack(MyListingsFragment())

                R.id.nav_payment_methods ->
                    openFragmentWithBackstack(PaymentFragment())

                R.id.nav_help -> {
                    // TODO: Add Help page later if needed
                }

                R.id.nav_logout -> {
                    finish()
                }
            }

            binding.drawerLayout.closeDrawer(GravityCompat.END)
            true
        }
    }
}
