package com.tudor.weatherappcompose.presentation.weather_forecast_dashboard.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tudor.weatherappcompose.data.remote.responses.WeatherForecastResponse
import com.tudor.weatherappcompose.domain.models.WeatherForecast
import com.tudor.weatherappcompose.domain.util.FahrenheitToCelsius
import com.tudor.weatherappcompose.domain.util.Resource
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//DAILY FORECAST
@SuppressLint("DefaultLocale")
@Composable
fun WeatherForecastDaily(
    modifier: Modifier,
    weatherInfo: Resource<WeatherForecast>,
) {
    Column(
        modifier = modifier
            .padding(PaddingValues(top = 20.dp, bottom = 20.dp)),
    ) {
        when (weatherInfo) {
            is Resource.Success -> {
                val numberOfForecastDays = 14

                for (i in 0..<numberOfForecastDays) {
                    val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                    val date: Date = format.parse(weatherInfo.data!!.dailyForecast[i].dateTime)
                        ?: throw IllegalArgumentException("Invalid date format")
                    val timeStampInMillis = date.time
                    val day = SimpleDateFormat(
                        "EEE",
                        Locale.ENGLISH
                    ).format(timeStampInMillis)
                        .toUpperCase(Locale.ENGLISH)

                    val minTemp = String.format(
                        "%.0f",
                        FahrenheitToCelsius(weatherInfo.data.dailyForecast[i].tempMin)
                    ).toDouble().toString()

                    val maxTemp = String.format(
                        "%.0f",
                        FahrenheitToCelsius(weatherInfo.data.dailyForecast[i].tempMax)
                    ).toDouble().toString()

                    val precipProb = "${weatherInfo.data.dailyForecast[i].precipProb}%"

                    if (i == 0) {
                        WeatherForecastDailyItem(
                            day = "Today",
                            minTemp = minTemp,
                            maxTemp = maxTemp,
                            precipProb = precipProb
                        )
                    } else {
                        WeatherForecastDailyItem(
                            day = day,
                            minTemp = minTemp,
                            maxTemp = maxTemp,
                            precipProb = precipProb
                        )
                    }
                }
            }

            is Resource.Loading -> {
                CircularProgressIndicator()
            }

            is Resource.Error -> {
                Log.e("loading error", weatherInfo.message.toString())

            }
        }
    }
}

@Composable
fun WeatherForecastDailyItem(
    modifier: Modifier = Modifier,
    day: String,
    minTemp: String,
    maxTemp: String,
    precipProb: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = modifier.weight(1f),
            text = day,
            fontSize = 20.sp
        )
        Row(
            modifier = modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = minTemp,
                fontSize = 20.sp,
                color = Color.Blue
            )
            Spacer(
                modifier = Modifier
                    .size(1.dp, 40.dp)
                    .background(Color.LightGray)
            )
            Text(
                text = maxTemp,
                fontSize = 20.sp,
                color = Color.Red
            )
        }
        Text(
            modifier = modifier.weight(1f),
            textAlign = TextAlign.Right,
            text = precipProb,
            fontSize = 20.sp
        )
    }
}