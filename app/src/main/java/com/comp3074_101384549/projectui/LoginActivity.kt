package com.comp3074_101384549.projectui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.comp3074_101384549.projectui.databinding.ActivityLoginBinding
import com.comp3074_101384549.projectui.HomeActivity
import com.comp3074_101384549.projectui.data.remote.ApiService
import com.comp3074_101384549.projectui.data.local.AuthPreferences
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    // In a Hilt setup, this would be @Inject lateInit var
    private lateinit var apiService: ApiService

    private lateinit var authPreferences: AuthPreferences // NEW FIELD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Note: You may need to create ActivityLoginBinding if it doesn't exist
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Manual initialization (replace with DI setup if using Hilt)
        authPreferences = AuthPreferences(applicationContext)
        // apiService = // You would initialize your Retrofit service here

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Start login coroutine (NEW)
            lifecycleScope.launch {
                try {
                    // Simplified: Perform API login call (need actual impl of apiService)

                    // --- DUMMY SUCCESS LOGIC FOR TESTING ---
                    val token = "dummy_jwt_token_for_${username}"
                    val dummyUserId = "user_${username.hashCode()}"
                    // --- END DUMMY LOGIC ---


                    // 2. Save the token and ID on success (NEW)
                    authPreferences.saveAuthDetails(token, dummyUserId)

                    // 3. Navigate to the main application
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "Login failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        // Note: I assume this button might be the 'Skip' button repurposed or a placeholder
        // If your original activity was `MainActivity.kt`, this should navigate back.
        binding.logoutButton.setOnClickListener {
            finish()
        }
    }
}