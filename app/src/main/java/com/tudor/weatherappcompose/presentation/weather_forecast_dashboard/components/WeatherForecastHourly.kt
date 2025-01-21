package com.tudor.weatherappcompose.presentation.weather_forecast_dashboard.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tudor.weatherappcompose.data.remote.responses.WeatherForecastResponse
import com.tudor.weatherappcompose.domain.models.WeatherForecast
import com.tudor.weatherappcompose.domain.util.FahrenheitToCelsius
import com.tudor.weatherappcompose.domain.util.Resource

//HOURLY
@SuppressLint("DefaultLocale")
@Composable
fun WeatherForecastHourly(
    modifier: Modifier,
    weatherInfo: Resource<WeatherForecast>
) {
    LazyRow(
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
        modifier = modifier
            .height(80.dp),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        when (weatherInfo) {
            is Resource.Success -> {
                val itemCount = weatherInfo.data!!.dailyForecast[0].hourlyForecast.size
                items(itemCount) { count ->
                    weatherInfo.data.dailyForecast[0].hourlyForecast[count].let {
                        WeatherForecastHourlyItem(
                            time = it.dateTime,
                            temperature = String.format(
                                "%.0f",
                                FahrenheitToCelsius(
                                    weatherInfo.data.dailyForecast[0].hourlyForecast[count].temp
                                )
                            )
                        ).toString()
                    }
                }
            }

            is Resource.Loading -> {
                item {
                    CircularProgressIndicator()

                }
            }

            is Resource.Error -> {
                item {
                    Log.e("loading error", weatherInfo.message.toString())

                }
            }
        }
    }
}

@Composable
fun WeatherForecastHourlyItem(
    time: String,
    temperature: String,
) {
    Column(
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier,
    ) {
        Text(
            text = time.substring(
                0,
                time.length - 3
            ),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(
            modifier = Modifier
                .size(60.dp, 10.dp)
        )
        Spacer(
            modifier = Modifier
                .size(60.dp, 1.dp)
                .background(Color.Black)
        )
        Spacer(
            modifier = Modifier
                .size(60.dp, 10.dp)
        )
        Text(
            text = "${temperature}°C",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}