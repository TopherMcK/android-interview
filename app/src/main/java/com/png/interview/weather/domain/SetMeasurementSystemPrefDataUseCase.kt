package com.png.interview.weather.domain

import android.content.SharedPreferences
import com.png.interview.weather.preferences.SettingsConstants.MEASUREMENT_SYSTEM_PREF_KEY
import javax.inject.Inject

interface SetMeasurementSystemPrefDataUseCase {
    suspend operator fun invoke(measurementSystem: String)
}

class DefaultSetMeasurementSystemPrefDataUseCase @Inject constructor(
    private val appPreferences: SharedPreferences
): SetMeasurementSystemPrefDataUseCase {
    override suspend fun invoke(measurementSystem: String) {
        appPreferences.edit().putString(MEASUREMENT_SYSTEM_PREF_KEY, measurementSystem)
            .apply()
    }
}