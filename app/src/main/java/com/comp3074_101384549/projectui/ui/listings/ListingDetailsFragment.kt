package com.comp3074_101384549.projectui.ui.listings

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.comp3074_101384549.projectui.BuildConfig
import com.comp3074_101384549.projectui.R
import com.comp3074_101384549.projectui.utils.DirectionsApiHelper
import com.comp3074_101384549.projectui.utils.MapUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import kotlinx.coroutines.launch

class ListingDetailsFragment : Fragment(), OnMapReadyCallback {
    private var googleMap: GoogleMap? = null
    private var currentPolyline: Polyline? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var destinationLatLng: LatLng? = null

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listing_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        // Get listing data from arguments
        val address = arguments?.getString("address") ?: "123 Main St"
        val price = arguments?.getDouble("price") ?: 0.0
        val availability = arguments?.getString("availability") ?: "N/A"
        val description = arguments?.getString("description") ?: "No description"

        // Display listing data
        view.findViewById<TextView>(R.id.textAddress)?.text = "Address: $address"
        view.findViewById<TextView>(R.id.textPrice)?.text = "Price: $$price/hour"
        view.findViewById<TextView>(R.id.textAvailability)?.text = "Available: $availability"
        view.findViewById<TextView>(R.id.textDescription)?.text = description

        destinationLatLng = MapUtils.getLatLngFromAddress(requireContext(), address)

        setupButtons(view)
    }

    private fun setupButtons(view: View) {
        view.findViewById<Button>(R.id.buttonShowRoute)?.setOnClickListener {
            showRoute()
        }

        view.findViewById<Button>(R.id.buttonNavigate)?.setOnClickListener {
            navigateToDestination()
        }

        view.findViewById<Button>(R.id.buttonReserve)?.setOnClickListener {
            Toast.makeText(requireContext(), "Navigating to payment...", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<Button>(R.id.buttonGoBack)?.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        destinationLatLng?.let { destination ->
            MapUtils.addMarker(map, destination, "Parking Spot", "Tap to view details")
            MapUtils.moveCameraToPosition(map, destination, 15f)
        }

        enableMyLocation()
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap?.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun showRoute() {
        val map = googleMap ?: return
        val destination = destinationLatLng ?: return

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(requireContext(), "Location permission required", Toast.LENGTH_SHORT).show()
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val origin = LatLng(location.latitude, location.longitude)

                lifecycleScope.launch {
                    val result = DirectionsApiHelper.getDirections(origin, destination, BuildConfig.MAPS_API_KEY)

                    if (result != null) {
                        currentPolyline?.remove()
                        currentPolyline = MapUtils.drawRoute(map, result.polylinePoints, Color.BLUE, 10f)

                        val points = listOf(origin, destination)
                        MapUtils.fitBounds(map, points, 150)

                        Toast.makeText(
                            requireContext(),
                            "${result.distance} • ${result.duration}",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(requireContext(), "Failed to get route", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Unable to get current location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToDestination() {
        destinationLatLng?.let { destination ->
            MapUtils.launchGoogleMapsNavigation(requireContext(), destination)
        } ?: run {
            Toast.makeText(requireContext(), "Destination not available", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation()
            }
        }
    }
}
