/*
 * Course: MAD302-01 Android Development
 * Assignment: Assignment 3 - Smart Utility App
 * Student Name: Darshilkumar Karkar
 * Student ID: A00203357
 * Date of Submission: 2026-04-17
 * Description: ViewModel for HomeFragment. Manages UI state for weather data
 *              fetching using LiveData and Kotlin coroutines.
 */

package com.example.assignment03

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * WeatherViewModel holds and manages UI-related data for HomeFragment.
 * Survives configuration changes (screen rotation).
 */
class WeatherViewModel : ViewModel() {

    // Backing property for weather data list
    private val _weatherList = MutableLiveData<List<WeatherItem>>()

    /** Observable weather data exposed to the UI. */
    val weatherList: LiveData<List<WeatherItem>> = _weatherList

    // Backing property for loading state
    private val _isLoading = MutableLiveData<Boolean>()

    /** Observable loading state — true while data is being fetched. */
    val isLoading: LiveData<Boolean> = _isLoading

    // Backing property for error messages
    private val _errorMessage = MutableLiveData<String?>()

    /** Observable error message — null when no error. */
    val errorMessage: LiveData<String?> = _errorMessage

    /**
     * Triggers an async fetch of weather data via [ApiService].
     * Updates [isLoading], [weatherList], and [errorMessage] accordingly.
     *
     * @param simulateError Pass true to test network error handling.
     */
    fun loadWeatherData(simulateError: Boolean = false) {
        // Show loading spinner
        _isLoading.value = true
        _errorMessage.value = null

        // Launch coroutine in the ViewModel scope (auto-cancelled on ViewModel clear)
        viewModelScope.launch {
            try {
                val data = ApiService.fetchWeatherData(simulateError)
                _weatherList.value = data
            } catch (e: Exception) {
                // Show a safe, user-friendly error message (not raw exception)
                _errorMessage.value = e.message ?: "An unexpected error occurred."
            } finally {
                // Always hide loading spinner when done
                _isLoading.value = false
            }
        }
    }
}