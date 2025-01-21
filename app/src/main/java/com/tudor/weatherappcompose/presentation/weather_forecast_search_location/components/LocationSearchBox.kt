package com.tudor.weatherappcompose.presentation.weather_forecast_search_location.components

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LocationSearchBox(
    modifier: Modifier,
    text: String,
    hint: String,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    onFocusChange: (FocusState) -> Unit,
    cornerRadius: Dp
) {
    Box(
        modifier = if (isHintVisible) {
            modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(cornerRadius))
        } else {
            modifier
                .fillMaxWidth()
                .background(Color.LightGray)

        },

        ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
                .onFocusChanged {
                    onFocusChange(it)
                }
        )
        if (isHintVisible) {
            Text(
                modifier = modifier
                    .padding(10.dp),
                text = hint,
                color = Color.DarkGray
            )
        }
    }
}