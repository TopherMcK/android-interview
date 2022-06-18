package com.png.interview.weather.ui.binder

import com.png.interview.weather.preferences.MeasurementSystem
import com.png.interview.weather.ui.viewmodel.SettingsViewModel

class SettingsFragmentBinder(
    private val viewModel: SettingsViewModel
) {
    val measurementSystem = viewModel.measureSystemViewRepresentation

    fun updateViewModelMeasurementSystemPref() {
        viewModel.updateViewModelMeasurementSystemPref()
    }

    fun setMeasurementSystem(measurementSystem: MeasurementSystem) {
        viewModel.setMeasurementSystemPreference(measurementSystem)
    }
}