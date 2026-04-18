/*
 * Course: MAD302-01 Android Development
 * Assignment: Assignment 3 - Smart Utility App
 * Student Name: Darshilkumar Karkar
 * Student ID: A00203357
 * Date of Submission: 24/04/2026
 * Description: Utility object for validating and sanitizing user input.
 *              Prevents injection attacks and ensures data integrity.
 */

package com.example.assignment03

/**
 * InputValidator provides static-style validation methods for user-supplied text.
 * All input should be validated here before being stored or processed.
 */
object InputValidator {

    /**
     * Validates that a city name input is safe and non-empty.
     * Rejects blank input, inputs that are too long, or inputs containing
     * dangerous characters (digits and special symbols not found in city names).
     *
     * @param input The raw string entered by the user.
     * @return A [ValidationResult] indicating success or a specific error message.
     */
    fun validateCityName(input: String): ValidationResult {
        val trimmed = input.trim()

        // Reject empty or blank input
        if (trimmed.isEmpty()) {
            return ValidationResult.Error("City name cannot be empty.")
        }

        // Reject suspiciously long input (potential injection attempt)
        if (trimmed.length > 50) {
            return ValidationResult.Error("City name is too long.")
        }

        // Only allow letters, spaces, hyphens, and apostrophes (valid in city names)
        val safePattern = Regex("^[a-zA-Z\\s'\\-]+$")
        if (!safePattern.matches(trimmed)) {
            return ValidationResult.Error("City name contains invalid characters.")
        }

        return ValidationResult.Success(trimmed)
    }

    /**
     * Sealed class representing the result of a validation check.
     * Use [Success] to get the sanitized value, [Error] for the message.
     */
    sealed class ValidationResult {
        /** Validation passed. Contains the sanitized input value. */
        data class Success(val sanitizedValue: String) : ValidationResult()

        /** Validation failed. Contains a safe, user-friendly error message. */
        data class Error(val message: String) : ValidationResult()
    }
}