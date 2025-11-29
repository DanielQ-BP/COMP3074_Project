package com.comp3074_101384549.projectui.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.comp3074_101384549.projectui.R
import com.comp3074_101384549.projectui.databinding.FragmentHomeBinding
import com.comp3074_101384549.projectui.model.Listing
import com.comp3074_101384549.projectui.repository.ListingRepository

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Load all listings initially
        updateListings(ListingRepository.getAllListings())

        binding.buttonSearch.setOnClickListener {
            val address = binding.editTextAddress.text.toString().trim()
            val maxPrice = binding.editTextMaxPrice.text.toString().toDoubleOrNull()

            val results = ListingRepository.searchListings(address, maxPrice)

            if (results.isEmpty()) {
                Toast.makeText(requireContext(), "No parking spots found", Toast.LENGTH_SHORT).show()
                updateListings(emptyList())
            } else {
                Toast.makeText(requireContext(), "Found ${results.size} parking spot(s)", Toast.LENGTH_SHORT).show()
                updateListings(results)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateListings(ListingRepository.getAllListings())
    }

    private fun updateListings(listings: List<Listing>) {
        val displayList = listings.map { "${it.address} - $${it.price}/hr" }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, displayList)
        binding.listViewListings.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}