package com.png.interview.weather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.png.interview.weather.domain.CreateGetMeasurementSystemPrefFromSettingUseCase
import com.png.interview.weather.domain.CreateSetMeasurementSystemPrefFromSettingUseCase
import com.png.interview.weather.preferences.MeasurementSystem
import com.png.interview.weather.preferences.MeasurementSystem.IMPERIAL
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val setMeasurementSystemSettingUseCase: CreateSetMeasurementSystemPrefFromSettingUseCase,
    private val getMeasurementSystemSettingUseCase: CreateGetMeasurementSystemPrefFromSettingUseCase
) : ViewModel() {

    private val _measurementSystemViewRepresentation = MutableStateFlow<MeasurementSystem>(IMPERIAL)

    fun updateViewModelMeasurementSystemPref() {
        viewModelScope.launch {
            _measurementSystemViewRepresentation.value = getMeasurementSystemSettingUseCase()
        }
    }

    fun setMeasurementSystemPreference(measurementSystemPref: MeasurementSystem) {
        viewModelScope.launch {
            _measurementSystemViewRepresentation.value = setMeasurementSystemSettingUseCase(measurementSystemPref)
        }
    }

    val measureSystemViewRepresentation = _measurementSystemViewRepresentation.map { it }.asLiveData()
}