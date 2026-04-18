/*
 * Course: MAD302-01 Android Development
 * Assignment: Assignment 3 - Smart Utility App
 * Student Name: YOUR FULL NAME
 * Student ID: YOUR STUDENT ID
 * Date of Submission: 2026-04-17
 * Description: Data model representing a single weather data item fetched from the API.
 */

package com.example.assignment03

/**
 * Data class representing one weather entry returned by the API.
 *
 * @param city The name of the city.
 * @param temperature Temperature in Celsius.
 * @param condition Short weather condition description (e.g., "Sunny").
 */
data class WeatherItem(
    val city: String,
    val temperature: Int,
    val condition: String
)