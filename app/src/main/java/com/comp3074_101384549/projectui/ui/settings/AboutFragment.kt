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
            ParkSpot is a mobile application that connects drivers who need convenient,
            affordable parking with nearby residents or businesses that have unused spaces.

            It aims to reduce circling for parking, unpredictable pricing, and uncertainty
            around availability — especially in busy areas.

            Drivers can:
            • Search for nearby parking based on price, distance, and time  
            • View detailed listings with location and information  
            • Reserve spots and receive booking confirmation

            Owners can:
            • List their available parking spots  
            • Set price, availability, and directions  
            • Manage active/inactive listings

            Developed by Group 12:
            • Israel Osunkoya
            • Andres Sanchez Alzate
            • Sara Mosquera Mayo
            • Daniel Quach
            
        """.trimIndent()

        binding.aboutText.text = aboutInfo
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
