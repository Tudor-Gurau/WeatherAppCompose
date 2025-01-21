package com.tudor.weatherappcompose.presentation.weather_forecast_dashboard.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tudor.weatherappcompose.R
import com.tudor.weatherappcompose.data.remote.responses.WeatherForecastResponse
import com.tudor.weatherappcompose.domain.models.WeatherForecast
import com.tudor.weatherappcompose.domain.util.FahrenheitToCelsius
import com.tudor.weatherappcompose.domain.util.Resource

//CURRENT WEATHER
@SuppressLint("DefaultLocale")
@Composable
fun CurrentConditionsBox(
    modifier: Modifier = Modifier,
    weatherInfo: Resource<WeatherForecast>,
    cornerRadius: Dp,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        when (weatherInfo) {
            is Resource.Loading -> {
                CircularProgressIndicator()
            }

            is Resource.Success -> {
                val tempInFahrenheit = weatherInfo.data!!.currentTemp
                val tempInCelsius =
                    String.format("%.2f", FahrenheitToCelsius(tempInFahrenheit)).toDouble()

                Row {
                    Text(
                        text = "$tempInCelsius",
                        textAlign = TextAlign.Right,
                        fontWeight = FontWeight.Bold,
                        fontSize = 60.sp,
                    )
                    Text(
                        text = "°C",
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                    )
                }
                Spacer(modifier = Modifier.height(100.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(10.dp, RoundedCornerShape(cornerRadius))
                        .background(Color.LightGray, RoundedCornerShape(cornerRadius)),
                ) {
                    CurrentConditionsSecondarySpecs(
                        icon = R.drawable.weather_pouring,
                        condition = "${weatherInfo.data.currentPrecip}%",
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    CurrentConditionsSecondarySpecs(
                        icon = R.drawable.water_percent,
                        condition = "${weatherInfo.data.currentHumid}%",
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    CurrentConditionsSecondarySpecs(
                        icon = R.drawable.weather_windy,
                        condition = "${weatherInfo.data.currentWindSpeed}",
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    CurrentConditionsSecondarySpecs(
                        icon = R.drawable.weather_sunset_down,
                        condition = weatherInfo.data.currentSunSet.substring(
                            0,
                            weatherInfo.data.currentSunSet.length - 3
                        ),
                        modifier = Modifier.weight(1f),
                    )
                }

            }

            is Resource.Error -> {
                Log.e("loading error", weatherInfo.message.toString())
            }
        }

    }
}

@Composable
fun CurrentConditionsSecondarySpecs(
    icon: Int,
    condition: String,
    modifier: Modifier,
) {
    Column(horizontalAlignment = CenterHorizontally, modifier = modifier.padding(2.dp)) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "RainIcon",
            modifier = Modifier
                .align(CenterHorizontally)
                .size(50.dp)
        )
        Spacer(
            modifier = Modifier
                .size(80.dp, 1.dp)
                .background(Color.Black)
        )
        Text(
            text = condition,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}