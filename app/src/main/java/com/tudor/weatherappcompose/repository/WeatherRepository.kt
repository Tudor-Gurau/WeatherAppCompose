package com.tudor.weatherappcompose.repository

import com.tudor.weatherappcompose.data.remote.WeatherApi
import com.tudor.weatherappcompose.data.remote.responses.WeatherForecast
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class WeatherForecastRepository @Inject constructor(
    val api: WeatherApi,
) {
    suspend fun getWeatherForecast(location: String, apiKey: String): WeatherForecast {
        return api.getWeatherForecast(location, apiKey)
    }
}




