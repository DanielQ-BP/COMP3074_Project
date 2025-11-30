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
import androidx.lifecycle.lifecycleScope
import com.comp3074_101384549.projectui.R
import com.comp3074_101384549.projectui.databinding.FragmentHomeBinding
import android.content.Context
import com.comp3074_101384549.projectui.data.local.AppDatabase
import com.comp3074_101384549.projectui.data.remote.ApiService
import com.comp3074_101384549.projectui.repository.ListingRepository
import com.comp3074_101384549.projectui.model.Listing
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var listingRepository: ListingRepository

    // If not using DI, you would need to instantiate or provide it here.

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val db = AppDatabase.getDatabase(context)
        val listingDao = db.listingDao()

        // TODO: replace with your real backend base URL when ready
        val retrofit = Retrofit.Builder()
            .baseUrl("https://example.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        listingRepository = ListingRepository(apiService, listingDao)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState) // Always call super in onViewCreated

        val addressInput = view.findViewById<EditText>(R.id.editTextAddress)
        val maxPriceInput = view.findViewById<EditText>(R.id.editTextMaxPrice)
        val searchButton = view.findViewById<Button>(R.id.buttonSearch)
        val listView = view.findViewById<ListView>(R.id.listViewListings)

        // Load all listings on startup
        // FIX 1: Must be called inside a coroutine scope
        loadAllListings(listView)

        searchButton.setOnClickListener {
            val address = addressInput.text.toString().trim()
            val maxPrice = maxPriceInput.text.toString().toDoubleOrNull()

            // Perform search
            // FIX 2: Must be called inside a coroutine scope
            lifecycleScope.launch {
                val results = listingRepository.searchListings(address, maxPrice)

                if (results.isEmpty()) {
                    Toast.makeText(requireContext(), "No parking spots found", Toast.LENGTH_SHORT).show()
                    updateListings(listView, emptyList())
                } else {
                    Toast.makeText(requireContext(), "Found ${results.size} parking spot(s)", Toast.LENGTH_SHORT).show()
                    updateListings(listView, results)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        view?.let {
            val listView = it.findViewById<ListView>(R.id.listViewListings)
            // Load all listings on resume
            // FIX 3: Must be called inside a coroutine scope
            loadAllListings(listView)
        }
    }

    private fun loadAllListings(listView: ListView) {
        // lifecycleScope to launch a coroutine that handles the suspending call
        lifecycleScope.launch {
            // NOTE: The repository instance must be initialized correctly for this to work.
            val listings = listingRepository.getAllListings()
            updateListings(listView, listings)
        }
    }

    private fun updateListings(listView: ListView, listings: List<Listing>) {
        val displayList = listings.map { "${it.address} - $${it.pricePerHour}/hr" } // Changed to pricePerHour based on Repository
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, displayList)
        listView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Good practice to clear the binding reference
        _binding = null
    }
}