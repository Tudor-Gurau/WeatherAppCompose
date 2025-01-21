package com.tudor.weatherappcompose.domain.models

data class WeatherForecastDaily(
    val dateTime: String,
    val tempMin: Double,
    val tempMax: Double,
    val precipProb: Double,
    val hourlyForecast: List<WeatherForecastHourly>,
)