package com.tudor.weatherappcompose.weatherforecastdashboard

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tudor.weatherappcompose.R
import com.tudor.weatherappcompose.data.remote.responses.WeatherForecast
import com.tudor.weatherappcompose.repository.WeatherForecastRepository
import com.tudor.weatherappcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherForecastDashboardViewModel @Inject constructor(
    val repository: WeatherForecastRepository,
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

    fun refreshWeather() {
        _refreshButton.value += 1
    }

    fun getWeather() {
        viewModelScope.launch {
            _weatherInfo.value = Resource.Loading()
            try {
                val weatherData = repository.getWeatherForecast(location, apiKey = api_key)
                _weatherInfo.value = Resource.Success(weatherData)
            } catch (e: Exception) {
                _weatherInfo.value = Resource.Error(e.toString())
            }
        }
    }
}


/*
    suspend fun getWeatherForecast(location: String) : Resource<WeatherForecast> {
        return repository.getWeatherForecast(location, apiKey = api_key)
    } */