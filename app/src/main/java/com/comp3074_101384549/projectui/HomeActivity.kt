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

        authPreferences = AuthPreferences(applicationContext)

        setupBottomNav()
        setupDrawerMenu()

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

    private fun setupDrawerMenu() {
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.nav_profile -> {
                    openFragment(ProfileFragment())
                    binding.drawerLayout.closeDrawers()
                    true
                }

                R.id.nav_listings_created -> {
                    openFragment(CreateListingFragment())
                    binding.drawerLayout.closeDrawers()
                    true
                }

                R.id.nav_listings_reserved -> {
                    openFragment(ReservedListingsFragment())
                    binding.drawerLayout.closeDrawers()
                    true
                }

                R.id.nav_my_listings -> {
                    openFragment(MyListingsFragment())
                    binding.drawerLayout.closeDrawers()
                    true
                }

                R.id.nav_payment_methods -> {
                    openFragment(PaymentFragment())
                    binding.drawerLayout.closeDrawers()
                    true
                }

                R.id.nav_logout -> {
                    performLogout()
                    binding.drawerLayout.closeDrawers()
                    true
                }

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


    private fun performLogout() {
        lifecycleScope.launch {
            authPreferences.clearAuthDetails()

            val intent = Intent(this@HomeActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
