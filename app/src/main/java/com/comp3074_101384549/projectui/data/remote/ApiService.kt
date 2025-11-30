package com.comp3074_101384549.projectui.data.remote


import com.comp3074_101384549.projectui.model.Listing
import com.comp3074_101384549.projectui.model.User
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

/**
 * Retrofit Service Interface defining all remote API endpoints.
 */
interface ApiService {

    // Login endpoint (returns a token string for simplicity)
    @POST("auth/login")
    // NOTE: In a real app, use dedicated Request/Response models instead of User/String
    suspend fun login(@Body user: User): String

    // Fetch all listings
    @GET("listings/all")
    suspend fun getRemoteListings(): List<Listing>

    // Post a new listing
    @POST("listings/create")
    suspend fun createListing(@Body listing: Listing): Listing
}