/*
 * Course: MAD302-01 Android Development
 * Assignment: Assignment 3 - Smart Utility App
 * Student Name: Darshilkumar Karkar
 * Student ID: A00203357
 * Date of Submission: 2026-04-17
 * Description: Simulates a remote API call using Kotlin coroutines and delay
 *              to mimic real network latency. Returns weather data or throws
 *              an exception to simulate network failure.
 */

package com.example.assignment03

import kotlinx.coroutines.delay

/**
 * ApiService provides simulated asynchronous data fetching.
 * In a real app this would use Retrofit or OkHttp to call a REST endpoint.
 */
object ApiService {

    // Simulated list of weather data returned by the "API"
    private val fakeData = listOf(
        WeatherItem("Toronto", 12, "Cloudy"),
        WeatherItem("New York", 18, "Sunny"),
        WeatherItem("London", 9, "Rainy"),
        WeatherItem("Tokyo", 22, "Partly Cloudy"),
        WeatherItem("Sydney", 25, "Clear")
    )

    /**
     * Simulates fetching weather data from a remote server.
     * Uses a 1.5 second delay to mimic network latency.
     *
     * @param simulateError If true, throws an exception to test error handling.
     * @return List of [WeatherItem] objects.
     * @throws Exception when simulateError is true, simulating a network failure.
     */
    suspend fun fetchWeatherData(simulateError: Boolean = false): List<WeatherItem> {
        // Simulate network delay
        delay(1500)

        // Simulate a network failure scenario
        if (simulateError) {
            throw Exception("Network unavailable. Please check your connection.")
        }

        return fakeData
    }
}