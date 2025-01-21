package com.tudor.weatherappcompose.presentation.weather_forecast_dashboard

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tudor.weatherappcompose.R
import com.tudor.weatherappcompose.domain.models.WeatherForecast
import com.tudor.weatherappcompose.domain.use_case.GetWeatherUseCase
import com.tudor.weatherappcompose.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherForecastDashboardViewModel(
    val getWeatherUseCase: GetWeatherUseCase,
    application: Application
) : ViewModel() {
    val api_key = application.resources.getString(R.string.API_KEY)
    val location = "London,UK"

    private val _refreshButton = MutableStateFlow(0)
    val refreshButton : StateFlow<Int> = _refreshButton

    private val _weatherInfo = MutableStateFlow<Resource<WeatherForecast>>(Resource.Loading())
    val weatherInfo: StateFlow<Resource<WeatherForecast>> = _weatherInfo

    init {
        getWeather()
    }

    fun getWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            _weatherInfo.value = Resource.Loading()
            try {
                val weatherData = getWeatherUseCase(location, apiKey = api_key)
                withContext(Dispatchers.Main) {
                    _weatherInfo.value = Resource.Success(weatherData)

                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _weatherInfo.value = Resource.Error(e.toString())
                }
            }
        }
    }

    fun refreshWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            _weatherInfo.value = Resource.Loading()
            try {
                val weatherData = getWeatherUseCase.refreshRemoteWeather(location, apiKey = api_key)
                withContext(Dispatchers.Main) {
                    _weatherInfo.value = Resource.Success(weatherData)

                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _weatherInfo.value = Resource.Error(e.toString())
                }
            }
        }
    }
}