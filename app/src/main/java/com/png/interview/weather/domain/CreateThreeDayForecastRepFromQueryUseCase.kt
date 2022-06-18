package com.png.interview.weather.domain

import com.png.interview.api.common_model.NetworkResponse
import com.png.interview.weather.api.model.Day
import com.png.interview.weather.api.model.ForecastResponse
import com.png.interview.weather.preferences.MeasurementSystem
import com.png.interview.weather.ui.model.ForecastDayViewData
import com.png.interview.weather.ui.model.ForecastViewRepresentation
import javax.inject.Inject

interface CreateThreeDayForecastRepFromQueryUseCase {
    suspend operator fun invoke(query: String): ForecastViewRepresentation
}

class DefaultCreateThreeDayForecastRepFromQueryUseCase @Inject constructor(
    private val getForecastDataUseCase: GetForecastDataUseCase,
    private val getMeasurementSystemUseCase: CreateGetMeasurementSystemPrefFromSettingUseCase
) : CreateThreeDayForecastRepFromQueryUseCase {
    override suspend fun invoke(query: String): ForecastViewRepresentation {
        return when (val result = getForecastDataUseCase(query)) {
            is NetworkResponse.Success -> {
                val measurementSystem = getMeasurementSystemUseCase()
                ForecastViewRepresentation.ForecastViewRep(
                    convertResponseToDays(result.body, measurementSystem)
                )
            }
            else -> {
                ForecastViewRepresentation.Error
            }
        }
    }

    private fun convertResponseToDays(foreCastResponse: ForecastResponse, measurementSystem: MeasurementSystem): List<ForecastDayViewData> =
        foreCastResponse.forecast.forecastday.map { forecastDay ->
            with(forecastDay) {
                val (minTemp, maxTemp, windSpeed) = getDisplaysForMeasurementSystem(measurementSystem, day)
                ForecastDayViewData(
                    date = date,
                    minTemp = minTemp,
                    maxTemp = maxTemp,
                    windSpeed = windSpeed,
                    condition = day.condition.text
                )
            }
        }

    private fun getDisplaysForMeasurementSystem(measurementPref: MeasurementSystem, day: Day): Triple<String, String, String> =
        when(measurementPref) {
            MeasurementSystem.IMPERIAL -> {
                val minTemp = "${day.mintemp_f} F"
                val maxTemp = "${day.maxtemp_f} F"
                val windSpeed = "${day.maxwind_mph} MPH"
                Triple(minTemp, maxTemp, windSpeed)
            }
            MeasurementSystem.METRIC -> {
                val minTemp = "${day.mintemp_c} C"
                val maxTemp = "${day.maxtemp_c} C"
                val windSpeed = "${day.maxwind_kph} KPH"
                Triple(minTemp, maxTemp, windSpeed)
            }
        }
}