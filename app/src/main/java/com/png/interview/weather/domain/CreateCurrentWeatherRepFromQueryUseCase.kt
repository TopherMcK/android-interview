package com.png.interview.weather.domain

import com.png.interview.api.common_model.NetworkResponse
import com.png.interview.weather.api.model.CurrentWeatherResponse
import com.png.interview.weather.preferences.MeasurementSystem
import com.png.interview.weather.preferences.MeasurementSystem.IMPERIAL
import com.png.interview.weather.preferences.MeasurementSystem.METRIC
import com.png.interview.weather.ui.model.AvailableWeatherViewData
import com.png.interview.weather.ui.model.CurrentWeatherViewRepresentation
import javax.inject.Inject

interface CreateCurrentWeatherRepFromQueryUseCase {
    suspend operator fun invoke(query: String): CurrentWeatherViewRepresentation
}

class DefaultCreateCurrentWeatherRepFromQueryUseCase @Inject constructor(
    private val getCurrentWeatherDataUseCase: GetCurrentWeatherDataUseCase,
    private val getMeasurementSystemUseCase: CreateGetMeasurementSystemPrefFromSettingUseCase
) : CreateCurrentWeatherRepFromQueryUseCase {
    override suspend fun invoke(query: String): CurrentWeatherViewRepresentation {
        return when (val result = getCurrentWeatherDataUseCase(query)) {
            is NetworkResponse.Success -> {
                val measurementSystemPref = getMeasurementSystemUseCase.invoke()
                with(result.body) {
                    val (temperature, windSpeed) = getDisplaysForMeasurementSystem(measurementSystemPref, result.body)
                    CurrentWeatherViewRepresentation.AvailableWeatherViewRep(
                        AvailableWeatherViewData(
                            name = location.name,
                            date = location.localtime,
                            temperature = temperature,
                            condition = current.condition.text,
                            windDirection = current.wind_dir,
                            windSpeed = windSpeed
                        ),
                        measurementSystemPref
                    )
                }
            }
            else -> {
                CurrentWeatherViewRepresentation.Error
            }
        }
    }

    private fun getDisplaysForMeasurementSystem(measurementPref: MeasurementSystem, body: CurrentWeatherResponse): Pair<String, String> =
        when(measurementPref) {
            IMPERIAL -> "${body.current.temp_f} F" to "${body.current.gust_mph} MPH"
            METRIC -> "${body.current.temp_c} C" to "${body.current.gust_kph} KPH"
        }
}