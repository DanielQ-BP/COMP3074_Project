package com.comp3074_101384549.projectui.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.comp3074_101384549.projectui.R
import com.comp3074_101384549.projectui.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val searchButton = view.findViewById<Button>(R.id.buttonSearch)
        searchButton.setOnClickListener {
            Toast.makeText(requireContext(), "Search clicked!", Toast.LENGTH_SHORT).show()
        }

        val listView = view.findViewById<ListView>(R.id.listViewListings)
        val dummyListings = listOf("860 Bordigon Trail - $4/hr", "12 Willow St. - $7/hr")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, dummyListings)
        listView.adapter = adapter
    }
}