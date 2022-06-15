package com.png.interview.weather.ui.model

data class ForecastDayViewData(
    val date: String,
    val minTemp: String,
    val maxTemp: String,
    val windSpeed: String,
    val condition: String
)
