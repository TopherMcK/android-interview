package com.png.interview.weather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.png.interview.weather.domain.CreateThreeDayForecastRepFromQueryUseCase
import com.png.interview.weather.ui.model.ForecastViewRepresentation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForecastViewModel @Inject constructor(
    private val createThreeDayForecastRepFromQueryUseCase: CreateThreeDayForecastRepFromQueryUseCase
) : ViewModel() {

    private val _forecastViewRepresentation = MutableStateFlow<ForecastViewRepresentation>(ForecastViewRepresentation.Empty)

    fun submitForecastQuery(query: String) {
        viewModelScope.launch {
            _forecastViewRepresentation.value = createThreeDayForecastRepFromQueryUseCase(query)
        }
    }

    val forecastViewDataLiveData =
        _forecastViewRepresentation
            .map { (it as? ForecastViewRepresentation.ForecastViewRep)?.data }
            .asLiveData()
}