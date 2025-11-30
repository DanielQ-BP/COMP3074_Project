package com.comp3074_101384549.projectui.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.comp3074_101384549.projectui.R
import com.comp3074_101384549.projectui.model.Listing

class ListingAdapter(private var listings: List<Listing>) :
    RecyclerView.Adapter<ListingAdapter.ListingViewHolder>() {

    class ListingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val address: TextView = itemView.findViewById(R.id.textAddress)
        val price: TextView = itemView.findViewById(R.id.textPrice)
        val availability: TextView = itemView.findViewById(R.id.textAvailability)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_listing, parent, false)
        return ListingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        val listing = listings[position]
        holder.address.text = listing.address
        holder.price.text = "Price: \$${listing.pricePerHour}"
        holder.availability.text = "Available: ${listing.availability}"
    }

    override fun getItemCount(): Int = listings.size

    /**
     * Replaces the adapter's data set with a new list and refreshes the RecyclerView.
     * This is called from the Fragment's Coroutine Scope after fetching data.
     */
    fun updateListings(newListings: List<Listing>) {
        this.listings = newListings
        notifyDataSetChanged()
    }
}