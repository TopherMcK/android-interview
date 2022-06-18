package com.png.interview.weather.domain

import com.png.interview.weather.preferences.MeasurementSystem
import com.png.interview.weather.preferences.MeasurementSystem.Companion.getMeasurementSystemByName
import javax.inject.Inject

interface CreateGetMeasurementSystemPrefFromSettingUseCase {
    suspend operator fun invoke(): MeasurementSystem
}

class DefaultCreateGetMeasurementSystemPrefFromSettingUseCase @Inject constructor(
    private val getMeasurementSystemPrefDataUseCase: GetMeasurementSystemPrefDataUseCase
): CreateGetMeasurementSystemPrefFromSettingUseCase {
    override suspend fun invoke(): MeasurementSystem {
        val sharePrefMeasurementSystem = getMeasurementSystemPrefDataUseCase.invoke()
        return getMeasurementSystemByName(sharePrefMeasurementSystem)
    }
}