package com.tudor.weatherappcompose.di

import android.app.Application
import androidx.room.Room
import com.tudor.weatherappcompose.data.data_local.WeatherDao
import com.tudor.weatherappcompose.data.data_local.WeatherDatabase
import com.tudor.weatherappcompose.data.data_local.WeatherDatabase.Companion.WEATHER_DATABASE_NAME
import com.tudor.weatherappcompose.data.data_local.WeatherForecastConverter
import com.tudor.weatherappcompose.data.remote.WeatherApi
import com.tudor.weatherappcompose.data.repository.WeatherRepositoryImpl
import com.tudor.weatherappcompose.domain.repository.WeatherRepository
import com.tudor.weatherappcompose.domain.use_case.GetWeatherUseCase
import com.tudor.weatherappcompose.domain.util.Constants.BASE_URL
import com.tudor.weatherappcompose.presentation.weather_forecast_dashboard.WeatherForecastDashboardViewModel
import com.tudor.weatherappcompose.presentation.weather_forecast_search_location.WeatherForecastSearchLocationViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    single { get<WeatherDatabase>().weatherDAO}
    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(WeatherApi::class.java)
    }
    single {
        Room.databaseBuilder(
            androidApplication(),
            WeatherDatabase::class.java,
            WEATHER_DATABASE_NAME
        )
            .build()
    }
    singleOf(::WeatherRepositoryImpl) { bind<WeatherRepository>()}
    single { GetWeatherUseCase(get()) }
    viewModel { WeatherForecastDashboardViewModel(get(), get<Application>()) }
    viewModel { WeatherForecastSearchLocationViewModel() }
}

/*
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesWeatherRepository(
        api: WeatherApi
    ) : WeatherRepository {
        return WeatherRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun getWeatherApi() : WeatherApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(WeatherApi::class.java)
    }
} */

