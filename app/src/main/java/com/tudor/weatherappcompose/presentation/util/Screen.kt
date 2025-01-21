package com.tudor.weatherappcompose.presentation.util

sealed class Screen(val route: String) {
    object WeatherForecastDashboard: Screen("weather_forecast_dashboard")
    object WeatherForecastSearchLocation: Screen("weather_forecast_search_location")
}