package com.tudor.weatherappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tudor.weatherappcompose.ui.theme.WeatherForecastTheme
import com.tudor.weatherappcompose.weatherforecastdashboard.WeatherForecastDashboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherForecastTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "weather_forecast_dashboard"
                ) {
                    composable("weather_forecast_dashboard") {
                        WeatherForecastDashboard()
                    }
                }
            }
        }
    }
}