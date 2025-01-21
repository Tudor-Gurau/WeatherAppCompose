package com.tudor.weatherappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tudor.weatherappcompose.presentation.ui.theme.WeatherForecastTheme
import com.tudor.weatherappcompose.presentation.util.Screen
import com.tudor.weatherappcompose.presentation.weather_forecast_dashboard.WeatherForecastDashboard
import com.tudor.weatherappcompose.presentation.weather_forecast_search_location.WeatherForecastSearchLocation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherForecastTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.WeatherForecastDashboard.route
                ) {
                    composable(route = Screen.WeatherForecastDashboard.route) {
                        WeatherForecastDashboard(navController = navController)
                    }
                    composable(route = Screen.WeatherForecastSearchLocation.route) {
                        WeatherForecastSearchLocation(navController = navController)
                    }
                }
            }
        }
    }
}