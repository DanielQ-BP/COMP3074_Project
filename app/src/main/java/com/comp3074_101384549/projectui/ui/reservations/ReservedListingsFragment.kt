package com.comp3074_101384549.projectui.ui.reservations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.comp3074_101384549.projectui.R

class ReservedListingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reserved_listing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listView = view.findViewById<ListView>(R.id.listViewReserved)
        val dummyReservations = listOf(
            "123 Main St - Oct 5, 6pm–9pm",
            "456 Oak Ave - Oct 7, 2pm–4pm"
        )
    }
}