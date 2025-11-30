package com.comp3074_101384549.projectui.ui.listings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope // <-- IMPORTANT: Add this import
import com.comp3074_101384549.projectui.R
import com.comp3074_101384549.projectui.repository.ListingRepository
import com.comp3074_101384549.projectui.ui.adapter.ListingAdapter
import kotlinx.coroutines.launch // <-- IMPORTANT: Add this import
import javax.inject.Inject // Assuming Dependency Injection (DI)

class MyListingsFragment : Fragment() {

    // --- Dependency Injection Placeholder ---
    // You need a valid instance of the repository to call its functions.
    @Inject
    lateinit var listingRepository: ListingRepository

    // You might also want to hold a reference to the adapter
    private lateinit var listingAdapter: ListingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_listings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewListings)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize adapter with an empty list initially
        listingAdapter = ListingAdapter(emptyList())
        recyclerView.adapter = listingAdapter

        // Load data immediately
        loadListings()
    }

    override fun onResume() {
        super.onResume()
        // Reload data whenever the fragment is brought back into view
        loadListings()
    }

    private fun loadListings() {
        // FIX: Wrap the suspending function call in a coroutine scope
        lifecycleScope.launch {
            // Call the suspend function to fetch data from the repository (Room/DB)
            val listings = listingRepository.getAllListings()
            // Update the adapter with the new data
            listingAdapter.updateListings(listings)
        }
    }
}