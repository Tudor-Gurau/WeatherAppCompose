package com.tudor.weatherappcompose.data.models

class DailyWeatherEntry(
    val dateTime: String,
    val maxTemp: Double,
    val minTemp: Double,
    val temp: Double,
    val feelsLikeTemp: Double,
    val precipProb: Double,
    val conditions: String,
    val icon: String,
)