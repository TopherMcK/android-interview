package com.png.interview.weather.ui.model

import com.png.interview.weather.preferences.MeasurementSystem

sealed class CurrentWeatherViewRepresentation {
    class AvailableWeatherViewRep(val data: AvailableWeatherViewData, val measurementSystem: MeasurementSystem) : CurrentWeatherViewRepresentation()
    object Empty : CurrentWeatherViewRepresentation()
    object Error : CurrentWeatherViewRepresentation()
}
