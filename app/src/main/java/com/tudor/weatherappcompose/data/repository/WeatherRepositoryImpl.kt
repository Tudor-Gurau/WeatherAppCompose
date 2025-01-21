package com.tudor.weatherappcompose.data.repository

import com.tudor.weatherappcompose.data.data_local.WeatherDao
import com.tudor.weatherappcompose.data.remote.WeatherApi
import com.tudor.weatherappcompose.data.remote.responses.WeatherForecastResponse
import com.tudor.weatherappcompose.domain.models.WeatherForecast
import com.tudor.weatherappcompose.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    val api: WeatherApi,
    val db: WeatherDao,
) : WeatherRepository {
    override suspend fun getRemoteWeatherForecast(location: String, apiKey: String): WeatherForecastResponse {
        return api.getWeatherForecast(location, apiKey)
    }

    override suspend fun getLocalWeatherForecast(): WeatherForecast {
        return db.getLocalWeatherForecast()
    }

    override suspend fun insertWeatherForecast(weatherForecast: WeatherForecast) {
        db.insertWeather(weatherForecast)
    }
}