package com.comp3074_101384549.projectui.ui.listings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope // <-- IMPORTANT: Added for coroutines
import com.comp3074_101384549.projectui.repository.ListingRepository
import androidx.navigation.fragment.findNavController
import com.comp3074_101384549.projectui.R
import com.comp3074_101384549.projectui.model.Listing
import kotlinx.coroutines.launch // <-- IMPORTANT: Added for coroutines
import javax.inject.Inject // Assuming Dependency Injection (DI)
import java.util.UUID

class CreateListingFragment : Fragment() {

    // --- Dependency Injection Placeholder ---
    // NOTE: Replace this with your actual DI mechanism (e.g., Hilt, Koin, or ViewModel)
    // to get a valid instance of ListingRepository.
    @Inject
    lateinit var listingRepository: ListingRepository
    // Make sure your application component injects this Fragment if you use @Inject here.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_listing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val address = view.findViewById<EditText>(R.id.editTextAddress)
        val price = view.findViewById<EditText>(R.id.editTextPrice)
        val availability = view.findViewById<EditText>(R.id.editTextAvailability)
        val timeWindow = view.findViewById<EditText>(R.id.editTextTimeWindow)
        val description = view.findViewById<EditText>(R.id.editTextDescription)
        val createButton = view.findViewById<Button>(R.id.buttonCreateListing)

        createButton.setOnClickListener {

            // Collect all user inputs
            val addr = address.text.toString()
            val priceValue = price.text.toString().toDoubleOrNull() ?: 0.0
            val avail = availability.text.toString()
            val time = timeWindow.text.toString()
            val desc = description.text.toString()

            if (addr.isEmpty() || avail.isEmpty() || time.isEmpty() || desc.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newListing = Listing(
                id = UUID.randomUUID().toString(), // Generate a unique ID
                pricePerHour = priceValue,
                availability = avail,
                description = desc,
                isActive = true, // Default to active when created
                latitude = 0.0, // <-- Placeholder: Replace with actual input if available
                longitude = 0.0, // <-- Placeholder: Replace with actual input if available
                address = addr,
            )

            // FIX: Launch a coroutine to call the suspending function
            lifecycleScope.launch {
                try {
                    // Call the new suspending function
                    listingRepository.saveNewListing(newListing) // <-- REPLACED addListing()

                    Toast.makeText(requireContext(), "Listing created!", Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressedDispatcher.onBackPressed()

                } catch (e: Exception) {
                    // Handle API/DB errors gracefully
                    Log.e("CreateListingFragment", "Crash during listing creation: $e", e)
                    Toast.makeText(requireContext(), "Failed to create listing: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}