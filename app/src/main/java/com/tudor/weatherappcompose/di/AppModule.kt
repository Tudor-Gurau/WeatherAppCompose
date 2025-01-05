package com.tudor.weatherappcompose.di

import com.tudor.weatherappcompose.data.remote.WeatherApi
import com.tudor.weatherappcompose.repository.WeatherForecastRepository
import com.tudor.weatherappcompose.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesWeatherRepository(
        api: WeatherApi
    ) = WeatherForecastRepository(api)

    @Singleton
    @Provides
    fun getWeatherApi() : WeatherApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(WeatherApi::class.java)
    }
}

