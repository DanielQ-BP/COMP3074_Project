package com.comp3074_101384549.projectui.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.comp3074_101384549.projectui.R
import com.comp3074_101384549.projectui.databinding.FragmentHomeBinding
import com.comp3074_101384549.projectui.repository.ListingRepository

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val addressInput = view.findViewById<EditText>(R.id.editTextAddress)
        val maxPriceInput = view.findViewById<EditText>(R.id.editTextMaxPrice)
        val searchButton = view.findViewById<Button>(R.id.buttonSearch)
        val listView = view.findViewById<ListView>(R.id.listViewListings)

        // Load all listings
        updateListings(listView, ListingRepository.getAllListings())

        searchButton.setOnClickListener {
            val address = addressInput.text.toString().trim()
            val maxPrice = maxPriceInput.text.toString().toDoubleOrNull()

            // Perform search
            val results = ListingRepository.searchListings(address, maxPrice)

            if (results.isEmpty()) {
                Toast.makeText(requireContext(), "No parking spots found", Toast.LENGTH_SHORT).show()
                updateListings(listView, emptyList())
            } else {
                Toast.makeText(requireContext(), "Found ${results.size} parking spot(s)", Toast.LENGTH_SHORT).show()
                updateListings(listView, results)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        view?.let {
            val listView = it.findViewById<ListView>(R.id.listViewListings)
            updateListings(listView, ListingRepository.getAllListings())
        }
    }

    private fun updateListings(listView: ListView, listings: List<com.comp3074_101384549.projectui.model.Listing>) {
        val displayList = listings.map { "${it.address} - $${it.price}/hr" }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, displayList)
        listView.adapter = adapter
    }
}