package com.example.composeclockexample

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ClockStyle(
    val shortLineLength: Dp = 15.dp,
    val longLineLength: Dp = 25.dp,
    val radius: Dp = 100.dp,
    val secondLineWidth: Dp = 1.dp,
    val minuteLineWidth: Dp = 3.dp,
    val hourLineWidth: Dp = 5.dp,
    val secondLineColor: Color = Color.Red,
    val minuteLineColor: Color = Color.Black,
    val hourLineColor: Color = Color.Black,
    val shortLineWidth: Dp = 1.dp,
    val longLineWidth: Dp = 2.dp,
)
