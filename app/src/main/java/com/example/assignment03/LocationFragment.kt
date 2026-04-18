/*
 * Course: MAD302-01 Android Development
 * Assignment: Assignment 3 - Smart Utility App
 * Student Name: YOUR FULL NAME
 * Student ID: YOUR STUDENT ID
 * Date of Submission: 2026-04-17
 * Description: Second screen of the app. Requests ACCESS_FINE_LOCATION permission
 *              at runtime and displays the device's GPS coordinates on grant.
 *              Handles permission denial gracefully with a user-friendly message.
 */

package com.example.assignment03

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.assignment03.databinding.FragmentLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

/**
 * LocationFragment requests and handles the ACCESS_FINE_LOCATION permission.
 * On success, it retrieves and displays the user's GPS coordinates.
 * On denial, it shows a safe, informative message without crashing.
 */
class LocationFragment : Fragment() {

    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!

    // FusedLocationProviderClient is Google's recommended API for location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    /**
     * Permission request launcher using the ActivityResult API.
     * Replaces the older onRequestPermissionsResult approach.
     */
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission granted — fetch location
                fetchLocation()
            } else {
                // Permission denied — show friendly message, hide coordinate view
                binding.tvPermissionDenied.visibility = View.VISIBLE
                binding.tvCoordinates.visibility = View.GONE
                binding.tvLocationStatus.text = "Permission denied."
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.btnGetLocation.setOnClickListener {
            checkAndRequestPermission()
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    /**
     * Checks if location permission is already granted.
     * If yes, fetches location. If no, launches the permission request dialog.
     */
    private fun checkAndRequestPermission() {
        when {
            // Permission already granted
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                fetchLocation()
            }
            // Permission not yet granted — request it
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    /**
     * Retrieves the last known location using FusedLocationProviderClient.
     * Suppresses lint warning because permission is checked before calling.
     */
    @SuppressLint("MissingPermission")
    private fun fetchLocation() {
        binding.tvLocationStatus.text = "Fetching location..."
        binding.tvPermissionDenied.visibility = View.GONE

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                // Format coordinates to 5 decimal places for readability
                val lat = String.format("%.5f", location.latitude)
                val lon = String.format("%.5f", location.longitude)
                binding.tvCoordinates.text = "Lat: $lat\nLon: $lon"
                binding.tvCoordinates.visibility = View.VISIBLE
                binding.tvLocationStatus.text = "Location retrieved successfully."
            } else {
                // lastLocation can be null if device has never computed a location
                binding.tvLocationStatus.text = "Could not retrieve location. Try moving outdoors."
            }
        }.addOnFailureListener { exception ->
            // Safe error message — never expose internal exception details to user
            binding.tvLocationStatus.text = "Location request failed. Please try again."
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}