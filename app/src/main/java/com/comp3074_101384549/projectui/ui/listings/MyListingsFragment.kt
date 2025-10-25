package com.comp3074_101384549.projectui.ui.listings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.comp3074_101384549.projectui.R
import com.comp3074_101384549.projectui.repository.ListingRepository
import com.comp3074_101384549.projectui.ui.adapter.ListingAdapter

class MyListingsFragment : Fragment() {

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

        val listings = ListingRepository.getAllListings()
        val adapter = ListingAdapter(listings)
        recyclerView.adapter = adapter
    }
}