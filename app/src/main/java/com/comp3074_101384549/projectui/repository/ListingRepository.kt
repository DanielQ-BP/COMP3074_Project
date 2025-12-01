package com.comp3074_101384549.projectui.repository

import com.comp3074_101384549.projectui.data.local.ListingDao
import com.comp3074_101384549.projectui.model.Listing
import com.comp3074_101384549.projectui.model.ListingEntity
import com.comp3074_101384549.projectui.data.remote.ApiService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ListingRepository @Inject constructor(
    private val apiService: ApiService,
    private val listingDao: ListingDao
) {

    // Helper function to convert a list of ListingEntity objects to a list of Listing objects
    private fun List<ListingEntity>.toListingList(): List<Listing> {
        return this.map { entity ->
            Listing(
                id = entity.id,
                address = entity.address,
                pricePerHour = entity.pricePerHour,
                availability = entity.availability,
                description = entity.description,
                isActive = entity.isActive,
                latitude = entity.latitude,
                longitude = entity.longitude
            )
        }
    }

    /**
     * Replaces the old synchronous getAllListings() by fetching all data from the local Room database.
     * NOTE: Must be called from a coroutine scope (e.g., lifecycleScope.launch).
     */
    suspend fun getAllListings(): List<Listing> {
        // Collects the latest list of entities from the DAO Flow and converts it to a simple List.
        return listingDao.getAllListings().first().toListingList()
    }

    /**
     * Replaces the old synchronous searchListings() by searching the local Room database.
     * NOTE: Must be called from a coroutine scope (e.g., lifecycleScope.launch).
     */
    suspend fun searchListings(address: String = "", maxPrice: Double? = null): List<Listing> {

        // 1. Get filtered data from the DAO (filters by address/description/availability)
        val addressQuery = if (address.isBlank()) "%" else "%$address%"
        val allMatchingEntities = listingDao.searchListings(addressQuery).first()

        // 2. Convert to Listing model
        var results = allMatchingEntities.toListingList()

        // 3. Filter by maxPrice in the Repository layer (since SQL query is complex for null maxPrice)
        if (maxPrice != null && maxPrice > 0) {
            results = results.filter { it.pricePerHour <= maxPrice }
        }

        return results
    }
    /**
     * Replaces the old synchronous addListing() by persisting the new listing locally
     * and sending it to the remote API.
     */
    suspend fun saveNewListing(listing: Listing) {
        // 1. Save to local database (cache)
        listingDao.insert(listing.toListingEntity())

        // 2. Send to remote API
        apiService.createListing(listing)
    }
}
