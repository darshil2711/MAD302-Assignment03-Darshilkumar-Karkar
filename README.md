# MAD302-ASSIGNMENT03 — Smart Utility App

**Course:** MAD302-01 Android Development  
**Assignment:** Assignment 3  
**Student Name:** Darshilkumar Karkar
**Student ID:** A00203357

## Description

Smart Utility App is an Android application built with Kotlin that demonstrates:

- **Async data fetching** using Kotlin Coroutines and ViewModel/LiveData
- **Runtime permissions** — requests ACCESS_FINE_LOCATION at runtime and displays GPS coordinates
- **Robust error handling** — simulates network failures, handles permission denial, validates all input
- **Security** — uses AndroidX EncryptedSharedPreferences (AES-256) for sensitive data; input sanitized with regex validation
- **Two-screen navigation** — HomeFragment (weather data) → LocationFragment (GPS), using Navigation Component

## Screens

| Screen | Description |
|--------|-------------|
| Home | Fetches weather data with a loading spinner; search input with validation |
| Location | Requests location permission; displays GPS coordinates or denial message |

## Architecture

- MVVM (ViewModel + LiveData)
- Single Activity, multiple Fragments
- Navigation Component
- Kotlin Coroutines for async work

## Security Practices

- No sensitive data stored in plain text
- All user input validated and sanitized before use
- Error messages are user-friendly (no raw exceptions shown)
- Encrypted storage via `EncryptedSharedPreferences`
