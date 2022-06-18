package com.png.interview.weather.domain

import android.content.SharedPreferences
import com.png.interview.weather.preferences.SettingsConstants.MEASUREMENT_SYSTEM_PREF_KEY
import javax.inject.Inject

interface GetMeasurementSystemPrefDataUseCase {
    suspend operator fun invoke(): String?
}

class DefaultGetMeasurementSystemPrefDataUseCase @Inject constructor(
    private val appPreferences: SharedPreferences
): GetMeasurementSystemPrefDataUseCase {
    override suspend fun invoke(): String? =
        appPreferences.getString(MEASUREMENT_SYSTEM_PREF_KEY, null)
}