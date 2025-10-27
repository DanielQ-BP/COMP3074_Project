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

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.homeFragmentContainer, HomeFragment())
                .commit()
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.homeFragmentContainer, HomeFragment())
                        .commit()
                    true
                }
                R.id.addFragment -> {
                    true
                }
                R.id.drawerMenu -> {
                    binding.drawerLayout.openDrawer(GravityCompat.END)
                    false
                }
                else -> false
            }
        }

        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.homeFragmentContainer, ProfileFragment())
                        .addToBackStack(null)
                        .commit()
                }
                R.id.nav_listings_created -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.homeFragmentContainer, CreateListingFragment())
                        .addToBackStack(null)
                        .commit()
                }
                R.id.nav_listings_reserved -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.homeFragmentContainer, ReservedListingsFragment())
                        .addToBackStack(null)
                        .commit()
                }
                R.id.nav_my_listings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.homeFragmentContainer, MyListingsFragment())
                        .addToBackStack(null)
                        .commit()
                }
                R.id.nav_payment_methods -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.homeFragmentContainer, PaymentFragment())
                        .addToBackStack(null)
                        .commit()
                }
                R.id.nav_help -> {

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