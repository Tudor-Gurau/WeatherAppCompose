package com.tudor.weatherappcompose.data.data_local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tudor.weatherappcompose.domain.models.WeatherForecast

@Database(
    entities = [WeatherForecast::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(
    WeatherForecastConverter::class
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val weatherDAO : WeatherDao

    companion object {
        const val WEATHER_DATABASE_NAME = "weather_db"
    }
}