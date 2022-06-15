package com.png.interview.weather.ui.model

sealed class ForecastViewRepresentation {
    data class ForecastViewRep(val data: List<ForecastDayViewData>): ForecastViewRepresentation()
    object Empty: ForecastViewRepresentation()
    object Error: ForecastViewRepresentation()
}
