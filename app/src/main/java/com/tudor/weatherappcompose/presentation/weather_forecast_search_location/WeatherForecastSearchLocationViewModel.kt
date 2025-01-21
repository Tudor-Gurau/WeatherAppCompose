package com.tudor.weatherappcompose.presentation.weather_forecast_search_location

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WeatherForecastSearchLocationViewModel(

) : ViewModel() {
    private val _searchVisibility = MutableStateFlow(false)
    val searchVisibility : StateFlow<Boolean> = _searchVisibility

    private val _textSearchLocation = MutableStateFlow("")
    val textSearchLocation : StateFlow<String> = _textSearchLocation

    private val _focusChanged = MutableStateFlow(false)
    val focusChanged : StateFlow<Boolean> = _focusChanged

    fun switchSearchVisibility() {
        _searchVisibility.value = true
    }

    fun enteredContentSearch(content: String) {
        _textSearchLocation.value = content
    }

    fun changeFocus() {
        _focusChanged.value = !_focusChanged.value
    }

}