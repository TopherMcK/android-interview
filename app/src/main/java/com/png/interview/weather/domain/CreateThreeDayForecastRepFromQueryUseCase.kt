package com.png.interview.weather.domain

import com.png.interview.api.common_model.NetworkResponse
import com.png.interview.weather.api.model.ForecastResponse
import com.png.interview.weather.ui.model.ForecastDayViewData
import com.png.interview.weather.ui.model.ForecastViewRepresentation
import javax.inject.Inject

interface CreateThreeDayForecastRepFromQueryUseCase {
    suspend operator fun invoke(query: String): ForecastViewRepresentation
}

class DefaultCreateThreeDayForecastRepFromQueryUseCase @Inject constructor(
    private val getForecastDataUseCase: GetForecastDataUseCase
) : CreateThreeDayForecastRepFromQueryUseCase {
    override suspend fun invoke(query: String): ForecastViewRepresentation {
        return when (val result = getForecastDataUseCase(query)) {
            is NetworkResponse.Success -> {
                ForecastViewRepresentation.ForecastViewRep(
                    convertResponseToDays(result.body)
                )
            }
            else -> {
                ForecastViewRepresentation.Error
            }
        }
    }

    private fun convertResponseToDays(foreCastResponse: ForecastResponse): List<ForecastDayViewData> =
        foreCastResponse.forecast.forecastday.map { forecastDay ->
            with(forecastDay) {
                ForecastDayViewData(
                    date = date,
                    minTemp = "${day.mintemp_f} F",
                    maxTemp = "${day.maxtemp_f} F",
                    windSpeed = "${day.maxwind_mph} MPH",
                    condition = day.condition.text
                )
            }
        }
}