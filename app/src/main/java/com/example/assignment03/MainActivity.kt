/*
 * Course: MAD302-01 Android Development
 * Assignment: Assignment 3 - Smart Utility App
 * Student Name: Darshilkumar Karkar
 * Student ID: A00203357
 * Date of Submission: 24/04/2026
 * Description: Entry point for Smart Utility App. Sets up navigation component
 *              and hosts all fragments via NavHostFragment.
 */

package com.example.assignment03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * MainActivity serves as the single Activity host for the app.
 * Navigation between screens is handled by the Navigation Component
 * defined in res/navigation/nav_graph.xml.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}