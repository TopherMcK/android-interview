package com.png.interview.weather.domain

import com.png.interview.weather.preferences.MeasurementSystem
import javax.inject.Inject

interface CreateSetMeasurementSystemPrefFromSettingUseCase {
    suspend operator fun invoke(measurementSytemPref: MeasurementSystem): MeasurementSystem
}

class DefaultCreateSetMeasurementSystemPrefFromSettingUseCase @Inject constructor(
    private val setMeasurementSystemPrefDataUseCase: SetMeasurementSystemPrefDataUseCase
): CreateSetMeasurementSystemPrefFromSettingUseCase {
    override suspend fun invoke(measurementSytemPref: MeasurementSystem): MeasurementSystem {
        setMeasurementSystemPrefDataUseCase(measurementSytemPref.name)
        return measurementSytemPref
    }
}