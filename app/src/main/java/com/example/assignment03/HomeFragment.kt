/*
 * Course: MAD302-01 Android Development
 * Assignment: Assignment 3 - Smart Utility App
 * Student Name: Darshilkumar Karkar
 * Student ID: A00203357
 * Date of Submission: 2026-04-17
 * Description: First screen of the app. Displays a list of weather data fetched
 *              asynchronously using coroutines. Shows a loading spinner during fetch
 *              and error messages on failure. Navigates to LocationFragment.
 */

package com.example.assignment03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment03.databinding.FragmentHomeBinding

/**
 * HomeFragment is the first screen of the Smart Utility App.
 * It fetches weather data asynchronously and displays it in a RecyclerView.
 * Handles loading state and network errors gracefully.
 */
class HomeFragment : Fragment() {

    // View binding to access layout views safely (no findViewById)
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // ViewModel scoped to this fragment
    private val viewModel: WeatherViewModel by viewModels()

    // Tracks whether we should simulate a network error on next fetch
    private var simulateError = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView with a vertical linear layout
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observe loading state — show/hide spinner
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        // Observe weather data — populate RecyclerView
        viewModel.weatherList.observe(viewLifecycleOwner) { list ->
            if (list != null && list.isNotEmpty()) {
                binding.recyclerView.adapter = WeatherAdapter(list)
                binding.tvError.visibility = View.GONE
            }
        }

        // Observe errors — show safe error message to user
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (!message.isNullOrEmpty()) {
                binding.tvError.text = message
                binding.tvError.visibility = View.VISIBLE
            }
        }

        // Retry button alternates between normal and simulated error fetch
        binding.btnRetry.setOnClickListener {
            simulateError = !simulateError
            binding.btnRetry.text = if (simulateError) "Fetch Normal Data" else "Simulate Error & Retry"
            viewModel.loadWeatherData(simulateError)
        }

        // Navigate to location screen
        binding.btnLocation.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_location)
        }

        // Search button with input validation
        binding.btnSearch.setOnClickListener {
            val rawInput = binding.etCitySearch.text.toString()
            when (val result = InputValidator.validateCityName(rawInput)) {
                is InputValidator.ValidationResult.Success -> {
                    binding.tilCitySearch.error = null
                    // Save last search securely — never in plain SharedPreferences
                    val securePrefs = SecurePreferences(requireContext())
                    securePrefs.saveString(SecurePreferences.KEY_LAST_SEARCH, result.sanitizedValue)
                    android.widget.Toast.makeText(
                        requireContext(),
                        "Searching for: ${result.sanitizedValue}",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
                is InputValidator.ValidationResult.Error -> {
                    // Show validation error inline — never crash or show raw exceptions
                    binding.tilCitySearch.error = result.message
                }
            }
        }

        // Restore last searched city from secure storage
        val securePrefs = SecurePreferences(requireContext())
        val lastSearch = securePrefs.getString(SecurePreferences.KEY_LAST_SEARCH)
        if (lastSearch.isNotEmpty()) {
            binding.etCitySearch.setText(lastSearch)
        }

        // Load data on first launch
        viewModel.loadWeatherData()
    }

    /**
     * Clean up binding reference to prevent memory leaks.
     * Binding should not be accessed after onDestroyView.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}