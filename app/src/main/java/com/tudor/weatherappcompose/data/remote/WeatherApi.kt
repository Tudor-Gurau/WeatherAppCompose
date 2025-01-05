package com.tudor.weatherappcompose.data.remote

import com.tudor.weatherappcompose.data.remote.responses.WeatherForecast
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("VisualCrossingWebServices/rest/services/timeline/{location}")
    suspend fun getWeatherForecast(
        @Path("location") location : String,
        @Query("key") apiKey: String,
    ) : WeatherForecast
}
