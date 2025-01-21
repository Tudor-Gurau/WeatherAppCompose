package com.tudor.weatherappcompose.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "weatherForecast"
)
data class WeatherForecast(
    @PrimaryKey(
        autoGenerate = false
    ) val id: Int? = 1,
    val currentTemp: Double,
    val currentPrecip: Double,
    val currentHumid: Double,
    val currentWindSpeed: Double,
    val currentSunSet: String,
    val dailyForecast: List<WeatherForecastDaily>,
)