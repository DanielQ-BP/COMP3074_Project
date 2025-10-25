package com.comp3074_101384549.projectui.repository

import com.comp3074_101384549.projectui.model.Listing

object ListingRepository {
    private val listings = mutableListOf<Listing>()

    fun getAllListings(): List<Listing> {
        return listings
    }

    fun addListing(listing: Listing) {
        listings.add(listing)
    }
}