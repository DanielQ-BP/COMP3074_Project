package com.comp3074_101384549.projectui.repository

import com.comp3074_101384549.projectui.model.Listing

object ListingRepository {
    private val listings = mutableListOf<Listing>()

    init {
        // Sample listings
        listings.add(Listing("860 Trail", 4.0, "Mon-Fri", "6pm-9pm", "outdoor parking"))
        listings.add(Listing("12 Wilson", 7.0, "Daily", "All day", "Covered parking spot"))
    }

    fun getAllListings(): List<Listing> {
        return listings
    }

    fun addListing(listing: Listing) {
        listings.add(listing)
    }

    fun searchListings(address: String = "", maxPrice: Double? = null): List<Listing> {
        var results = listings.toList()

        // Filter by address if provided
        if (address.isNotEmpty()) {
            results = results.filter {
                it.address.contains(address, ignoreCase = true)
            }
        }

        // Filter by max price if provided
        if (maxPrice != null && maxPrice > 0) {
            results = results.filter { it.price <= maxPrice }
        }

        return results
    }
}