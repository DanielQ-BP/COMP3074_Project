package com.comp3074_101384549.projectui.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.comp3074_101384549.projectui.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aboutInfo = """
            ParkSpot is a mobile application designed to connect drivers with nearby
            residents or businesses who have unused parking spaces.

            The goal is to reduce the stress of circling for parking, unpredictable
            costs, and inconsistent availability — especially in high-traffic areas.

            With ParkSpot, drivers can:
            • Search for nearby parking based on price, distance, and time  
            • View listings with photos, directions, pricing, and availability  
            • Reserve a spot safely and make a secure payment  
            • Receive booking confirmation and reminders

            Parking spot owners can:
            • Create detailed listings  
            • Set price, availability, and directions  
            • Manage active or inactive listings  
            • Earn passive income from unused parking spaces

            This project was developed by Group 12:

            • Israel Osunkoya
            • Sara Mosquera Mayo  
            • Daniel Quach  
            • Andres Sanchez Alzate

            © 2025 ParkSpot. All rights reserved.
        """.trimIndent()

        binding.aboutText.text = aboutInfo
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
