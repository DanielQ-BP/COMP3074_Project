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

class ListingDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listing_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val reserveButton = view.findViewById<Button>(R.id.buttonReserve)
        reserveButton.setOnClickListener {
            Toast.makeText(requireContext(), "Navigating to payment...", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.paymentFragment)
        }
    }
}
