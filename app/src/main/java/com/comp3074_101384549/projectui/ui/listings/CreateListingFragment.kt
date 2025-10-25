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
import com.comp3074_101384549.projectui.repository.ListingRepository
import androidx.navigation.fragment.findNavController
import com.comp3074_101384549.projectui.R
import com.comp3074_101384549.projectui.model.Listing

class CreateListingFragment : Fragment() {

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
            try {
                val addr = address.text.toString()
                val priceValue = price.text.toString().toDoubleOrNull() ?: 0.0
                val avail = availability.text.toString()
                val time = timeWindow.text.toString()
                val desc = description.text.toString()

                if (addr.isEmpty() || avail.isEmpty() || time.isEmpty() || desc.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val newListing = Listing(addr, priceValue, avail, time, desc)
                ListingRepository.addListing(newListing)

                Toast.makeText(requireContext(), "Listing created!", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressedDispatcher.onBackPressed()
                // findNavController().navigate(R.id.myListingsFragment)

            } catch (e: Exception) {
                Log.e("CreateListingFragment", "Crash during listing creation", e)
                Toast.makeText(requireContext(), "Crash: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
