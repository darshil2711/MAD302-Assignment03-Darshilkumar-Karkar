/*
 * Course: MAD302-01 Android Development
 * Assignment: Assignment 3 - Smart Utility App
 * Student Name: YOUR FULL NAME
 * Student ID: YOUR STUDENT ID
 * Date of Submission: 2026-04-17
 * Description: RecyclerView adapter that binds WeatherItem data to list item views.
 */

package com.example.assignment03

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView adapter for displaying a list of [WeatherItem] objects.
 *
 * @param items The list of weather items to display.
 */
class WeatherAdapter(private val items: List<WeatherItem>) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    /**
     * ViewHolder caches references to the views in each list item.
     *
     * @param itemView The inflated view for a single row.
     */
    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCity: TextView = itemView.findViewById(R.id.tvCity)
        val tvTemp: TextView = itemView.findViewById(R.id.tvTemperature)
        val tvCondition: TextView = itemView.findViewById(R.id.tvCondition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        // Inflate the item layout for each row
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather, parent, false)
        return WeatherViewHolder(view)
    }

    /**
     * Binds data from a [WeatherItem] to the ViewHolder's views.
     *
     * @param holder The ViewHolder to bind data into.
     * @param position Index of the item in the list.
     */
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = items[position]
        holder.tvCity.text = item.city
        holder.tvTemp.text = "${item.temperature}°C"
        holder.tvCondition.text = item.condition
    }

    /** Returns the total number of items in the list. */
    override fun getItemCount(): Int = items.size
}