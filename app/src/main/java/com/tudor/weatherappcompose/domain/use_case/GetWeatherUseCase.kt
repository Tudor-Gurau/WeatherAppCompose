package com.tudor.weatherappcompose.domain.use_case

import com.tudor.weatherappcompose.domain.models.WeatherForecast
import com.tudor.weatherappcompose.domain.models.WeatherForecastDaily
import com.tudor.weatherappcompose.domain.models.WeatherForecastHourly
import com.tudor.weatherappcompose.domain.repository.WeatherRepository

class GetWeatherUseCase(
   private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke (location: String, apiKey: String) : WeatherForecast {
        val databaseWeather = weatherRepository.getLocalWeatherForecast()

        if(databaseWeather != null) {
            return databaseWeather
        }

        return getRemoteWeather(location, apiKey)
    }

    private suspend fun getRemoteWeather(location: String, apiKey: String) : WeatherForecast {
        val remoteWeather = weatherRepository.getRemoteWeatherForecast(location, apiKey)
        val localWeatherForecast : WeatherForecast
        remoteWeather.let {
            val currentTemp = it.currentConditions.temp
            val currentPrecip = it.currentConditions.precip
            val currentHumid = it.currentConditions.humidity
            val currentWindSpeed = it.currentConditions.windspeed
            val currentSunSet = it.currentConditions.sunset

            val dailyForecast = mutableListOf<WeatherForecastDaily>()
            for (i in it.days) {
                val dateTime = i.datetime
                val tempMin = i.tempmin
                val tempMax = i.tempmax
                val precipProb = i.precipprob

                val hourlyForecast = mutableListOf<WeatherForecastHourly>()
                for (j in i.hours) {
                    val dateTimeHourly = j.datetime
                    val temp = j.temp
                    hourlyForecast.add(
                        WeatherForecastHourly(
                            dateTimeHourly,
                            temp,
                        )
                    )
                }

                val daily = WeatherForecastDaily(
                    dateTime = dateTime,
                    tempMin = tempMin,
                    tempMax = tempMax,
                    precipProb = precipProb,
                    hourlyForecast = hourlyForecast
                )
                dailyForecast.add(daily)
            }

            localWeatherForecast = WeatherForecast(
                currentTemp = currentTemp,
                currentPrecip = currentPrecip,
                currentHumid = currentHumid,
                currentWindSpeed = currentWindSpeed,
                currentSunSet = currentSunSet,
                dailyForecast = dailyForecast

            )
        }

        weatherRepository.insertWeatherForecast(localWeatherForecast)
        return localWeatherForecast

    }

    suspend fun refreshRemoteWeather(location: String, apiKey: String) : WeatherForecast{
        return getRemoteWeather(location, apiKey)
    }
}