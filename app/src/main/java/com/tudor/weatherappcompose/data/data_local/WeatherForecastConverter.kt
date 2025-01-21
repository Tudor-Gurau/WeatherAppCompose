package com.tudor.weatherappcompose.data.data_local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tudor.weatherappcompose.domain.models.WeatherForecastDaily

class WeatherForecastConverter {

    @TypeConverter
    fun fromWeatherForecastDaily(value : List<WeatherForecastDaily>) : String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toWeatherForecastDaily(value: String) : List<WeatherForecastDaily>? {
        val type = object : TypeToken<List<WeatherForecastDaily>>() {}.type
        return Gson().fromJson(value, type)
    }
}