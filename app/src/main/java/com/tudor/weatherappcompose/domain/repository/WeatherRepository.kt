package com.tudor.weatherappcompose.domain.repository

import com.tudor.weatherappcompose.data.remote.responses.WeatherForecastResponse
import com.tudor.weatherappcompose.domain.models.WeatherForecast


interface WeatherRepository {
    suspend fun getRemoteWeatherForecast(location: String, apiKey: String) : WeatherForecastResponse
    suspend fun getLocalWeatherForecast() : WeatherForecast?
    suspend fun insertWeatherForecast(weatherForecast: WeatherForecast)
}