package com.tudor.weatherappcompose.presentation.weather_forecast_search_location

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tudor.weatherappcompose.presentation.weather_forecast_search_location.components.LocationSearchBox
import org.koin.androidx.compose.koinViewModel
@Composable
fun WeatherForecastSearchLocation(
    viewModel: WeatherForecastSearchLocationViewModel =  koinViewModel(),
    navController: NavController,
) {
    val isHintVisible = viewModel.focusChanged.collectAsState()
    val hint = "Search location..."
    val textSearch = viewModel.textSearchLocation.collectAsState()
    val cornerRadius = remember {
        16.dp
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(20.dp)
        ) {
            LocationSearchBox(
                modifier = Modifier,
                text = textSearch.value,
                hint = hint,
                onFocusChange = {
                    viewModel.changeFocus()
                },
                onValueChange = {
                    viewModel.enteredContentSearch(it)
                },
                singleLine = true,
                isHintVisible = isHintVisible.value,
                cornerRadius = cornerRadius
            )
        }

    }
}