package com.tudor.weatherappcompose.data.data_local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tudor.weatherappcompose.data.remote.responses.WeatherForecastResponse
import com.tudor.weatherappcompose.domain.models.WeatherForecast

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weatherForecast")
    fun getLocalWeatherForecast() : WeatherForecast

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weatherForecast: WeatherForecast)
}