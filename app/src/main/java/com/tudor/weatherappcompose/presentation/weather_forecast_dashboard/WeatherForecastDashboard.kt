package com.tudor.weatherappcompose.presentation.weather_forecast_dashboard

import androidx.compose.animation.Animatable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tudor.weatherappcompose.presentation.util.Screen
import com.tudor.weatherappcompose.presentation.weather_forecast_dashboard.components.CurrentConditionsBox
import com.tudor.weatherappcompose.presentation.weather_forecast_dashboard.components.WeatherForecastDaily
import com.tudor.weatherappcompose.presentation.weather_forecast_dashboard.components.WeatherForecastHourly
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WeatherForecastDashboard(
    viewModel: WeatherForecastDashboardViewModel = koinViewModel(),
    navController: NavController
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val pullState = rememberPullToRefreshState()
    val weatherInfo by viewModel.weatherInfo.collectAsState()
    val refreshTrigger by viewModel.refreshButton.collectAsState()
    val cornerRadius = remember {
        16.dp
    }

    LaunchedEffect(refreshTrigger) {
        viewModel.getWeather()
    }
    Scaffold(
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = {
                    navController.navigate(Screen.WeatherForecastSearchLocation.route)
                },
                contentColor = MaterialTheme.colors.primary,
                containerColor = MaterialTheme.colors.background
            ) {
                Icon(Icons.Filled.LocationOn, "Small floating action button.")
            }
        },
    ) { innerPadding ->

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                viewModel.refreshWeather()
            },
            state = pullState,
        ) {
            LaunchedEffect(isRefreshing) { }
            if (isRefreshing) {
                isRefreshing = false
            }
            LazyColumn(
                contentPadding = PaddingValues(top = 20.dp, bottom = 20.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
                    .padding(
                        start = innerPadding.calculateStartPadding(
                            LocalLayoutDirection.current
                        ) + 20.dp,
                        end = innerPadding.calculateEndPadding(
                            LocalLayoutDirection.current
                        ) + 20.dp,
                    )
            ) {
                item {
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
                        weatherInfo = weatherInfo,
                        cornerRadius = cornerRadius,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
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
                }
                item {
                    WeatherForecastDaily(
                        modifier = Modifier
                            .fillMaxSize()
                            .shadow(
                                10.dp,
                                RoundedCornerShape(cornerRadius)
                            )
                            .clip(
                                RoundedCornerShape(cornerRadius)
                            )
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
    }
}
