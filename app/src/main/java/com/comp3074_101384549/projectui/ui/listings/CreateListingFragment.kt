package com.comp3074_101384549.projectui.ui.listings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.comp3074_101384549.projectui.R

class CreateListingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_listing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val createButton = view.findViewById<Button>(R.id.buttonCreateListing)
        createButton.setOnClickListener {
            Toast.makeText(requireContext(), "Listing created!", Toast.LENGTH_SHORT).show()

            // Optional: Navigate to confirmation or listings screen
            // findNavController().navigate(R.id.listingsCreatedFragment)
        }
    }
}