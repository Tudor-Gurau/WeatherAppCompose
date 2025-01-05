package com.tudor.weatherappcompose.weatherforecastdashboard

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tudor.weatherappcompose.data.remote.responses.WeatherForecast
import com.tudor.weatherappcompose.util.FahrenheitToCelsius
import com.tudor.weatherappcompose.util.Resource
import com.tudor.weatherappcompose.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WeatherForecastDashboard(
    viewModel: WeatherForecastDashboardViewModel = hiltViewModel()
) {
    val weatherInfo by viewModel.weatherInfo.collectAsState()
    val refreshTrigger by viewModel.refreshButton.collectAsState()
    val cornerRadius = remember {
        30.dp
    }

    LaunchedEffect(refreshTrigger) {
        viewModel.getWeather()
    }

    Scaffold(
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = {
                    viewModel.refreshWeather()
                },
                contentColor = MaterialTheme.colors.primary,
                containerColor = MaterialTheme.colors.background
            ) {
                Icon(Icons.Filled.Refresh, "Small floating action button.")
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(
                    top = innerPadding.calculateTopPadding() + 20.dp,
                    bottom = innerPadding.calculateBottomPadding(),
                    start = innerPadding.calculateStartPadding(
                        LocalLayoutDirection.current
                    ) + 20.dp,
                    end = innerPadding.calculateEndPadding(
                        LocalLayoutDirection.current
                    ) + 20.dp,
                )
        ) {
            CurrentConditionsBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .shadow(
                        10.dp,
                        RoundedCornerShape(cornerRadius)
                    )
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(Color.White, RoundedCornerShape(cornerRadius))
                    .padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
                weatherInfo = weatherInfo
            )
            Spacer(modifier = Modifier.height(20.dp))
            WeatherForecastHourly(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        10.dp,
                        RoundedCornerShape(cornerRadius)
                    )
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(Color.White, RoundedCornerShape(cornerRadius))
                    .padding(top = 20.dp, bottom = 20.dp),
                weatherInfo = weatherInfo
            )
            Spacer(modifier = Modifier.height(20.dp))
            WeatherForecastDaily(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        10.dp,
                        RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius)
                    )
                    .clip(RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius))
                    .background(
                        Color.White,
                        RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius)
                    )
                    .padding(start = 20.dp, end = 20.dp),
                weatherInfo = weatherInfo
            )
        }
    }
}

//CURRENT WEATHER
@SuppressLint("DefaultLocale")
@Composable
fun CurrentConditionsBox(
    modifier: Modifier = Modifier,
    weatherInfo: Resource<WeatherForecast>
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
                val tempInFahrenheit = weatherInfo.data!!.currentConditions.temp
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(10.dp, RoundedCornerShape(20.dp))
                        .background(Color.LightGray, RoundedCornerShape(20.dp)),
                ) {
                    CurrentConditionsSecondarySpecs(
                        icon = R.drawable.weather_pouring,
                        condition = "${weatherInfo.data.currentConditions.precipprob}%",
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    CurrentConditionsSecondarySpecs(
                        icon = R.drawable.water_percent,
                        condition = "${weatherInfo.data.currentConditions.humidity}%",
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    CurrentConditionsSecondarySpecs(
                        icon = R.drawable.weather_windy,
                        condition = "${weatherInfo.data.currentConditions.windspeed}",
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    CurrentConditionsSecondarySpecs(
                        icon = R.drawable.weather_sunset_down,
                        condition = weatherInfo.data.currentConditions.sunset.substring(
                            0,
                            weatherInfo.data.currentConditions.sunset.length - 3
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
                val itemCount = weatherInfo.data!!.days[0].hours.size
                items(itemCount) { count ->
                    WeatherForecastHourlyItem(
                        time = weatherInfo.data.days[0].hours[count].datetime,
                        temperature = String.format(
                            "%.0f",
                            FahrenheitToCelsius(weatherInfo.data.days[0].hours[count].temp)
                        ).toDouble().toString(),
                    )
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

//DAILY FORECAST
@SuppressLint("DefaultLocale")
@Composable
fun WeatherForecastDaily(
    modifier: Modifier,
    weatherInfo: Resource<WeatherForecast>,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 20.dp, bottom = 20.dp),
    ) {
        when (weatherInfo) {
            is Resource.Success -> {
                val itemCount = weatherInfo.data!!.days.size
                items(itemCount) { count ->

                    val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                    val date: Date = format.parse(weatherInfo.data.days[count].datetime)
                        ?: throw IllegalArgumentException("Invalid date format")
                    val timeStampInMillis = date.time
                    val day = SimpleDateFormat(
                        "EEE",
                        Locale.ENGLISH
                    ).format(timeStampInMillis)
                        .toUpperCase(Locale.ENGLISH)

                    val minTemp = String.format(
                        "%.0f",
                        FahrenheitToCelsius(weatherInfo.data.days[count].tempmin)
                    ).toDouble().toString()

                    val maxTemp = String.format(
                        "%.0f",
                        FahrenheitToCelsius(weatherInfo.data.days[count].tempmax)
                    ).toDouble().toString()

                    val precipProb = "${weatherInfo.data.days[count].precipprob}%"

                    if (count == 0) {
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
fun WeatherForecastDailyItem(
    modifier: Modifier = Modifier,
    day: String,
    minTemp: String,
    maxTemp: String,
    precipProb: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = day, fontSize = 20.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            Text(text = minTemp, fontSize = 20.sp, color = Color.Blue)
            Spacer(
                modifier = Modifier
                    .size(1.dp, 40.dp)
                    .background(Color.LightGray)
            )
            Text(text = maxTemp, fontSize = 20.sp, color = Color.Red)
        }
        Text(text = precipProb, fontSize = 20.sp)
    }
}