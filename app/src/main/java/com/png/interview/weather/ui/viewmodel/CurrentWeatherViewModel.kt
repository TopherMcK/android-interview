package com.png.interview.weather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.png.interview.weather.domain.CreateAutocompleteRepFromQueryUseCase
import com.png.interview.weather.domain.CreateCurrentWeatherRepFromQueryUseCase
import com.png.interview.weather.domain.CreateGetMeasurementSystemPrefFromSettingUseCase
import com.png.interview.weather.preferences.MeasurementSystem
import com.png.interview.weather.ui.model.AutocompleteViewRepresentation
import com.png.interview.weather.ui.model.CurrentWeatherViewRepresentation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrentWeatherViewModel @Inject constructor(
    private val createCurrentWeatherRepFromQueryUseCase: CreateCurrentWeatherRepFromQueryUseCase,
    private val createAutocompleteRepFromQueryUseCase: CreateAutocompleteRepFromQueryUseCase,
    private val getMeasurementSystemPrefFromSettingUseCase: CreateGetMeasurementSystemPrefFromSettingUseCase
) : ViewModel() {

    private val _currentWeatherViewRepresentation = MutableStateFlow<CurrentWeatherViewRepresentation>(CurrentWeatherViewRepresentation.Empty)
    private val _autocompleteViewRepresentation = MutableStateFlow<AutocompleteViewRepresentation>(AutocompleteViewRepresentation.Empty)

    fun submitCurrentWeatherSearch(query: String) {
        viewModelScope.launch {
            _currentWeatherViewRepresentation.value = createCurrentWeatherRepFromQueryUseCase(query)
        }
    }

    fun submitCurrentWeatherSearch(itemPosition: Int) {
        val currentRep = _autocompleteViewRepresentation.value
        if(currentRep is AutocompleteViewRepresentation.AutocompleteViewRep)
            currentRep.data.elementAtOrNull(itemPosition)?.let {
                displayedInput = it.suggestion
                submitCurrentWeatherSearch(it.suggestion)
                setAutocompleteEmpty()
            }
    }

    val availableCurrentWeatherLiveData =
        _currentWeatherViewRepresentation
            .map {
                (it as? CurrentWeatherViewRepresentation.AvailableWeatherViewRep)?.let { rep ->
                    displayedMeasurementSystem = rep.measurementSystem
                    rep.data
                }
            }
            .asLiveData()

    val isEmptyVisible =
        _currentWeatherViewRepresentation
            .map { it is CurrentWeatherViewRepresentation.Empty }
            .asLiveData()

    val isQueryErrorMsgVisible =
        _currentWeatherViewRepresentation
            .map { it is CurrentWeatherViewRepresentation.Error }
            .asLiveData()

    val autocompleteLiveData =
        _autocompleteViewRepresentation.map {
            (it as? AutocompleteViewRepresentation.AutocompleteViewRep)?.data
        }.asLiveData()

    fun submitAutocompleteQuery(query: String) =
        viewModelScope.launch {
            _autocompleteViewRepresentation.value = createAutocompleteRepFromQueryUseCase(query)
        }

    fun setAutocompleteEmpty() {
        if(_autocompleteViewRepresentation.value.isVisibleRep())
            _autocompleteViewRepresentation.value = AutocompleteViewRepresentation.Empty
    }

    val hasAutocompleteSuggestionsVisible =
        _autocompleteViewRepresentation.map {
            it.isVisibleRep()
        }.asLiveData()

    private fun AutocompleteViewRepresentation.isVisibleRep() =
        this is AutocompleteViewRepresentation.AutocompleteViewRep &&
                this.data.isNotEmpty()

    var displayedInput = ""

    var displayedMeasurementSystem: MeasurementSystem? = null

    fun updateForMeasurementSystemChangeIFNeeded() {
        if(displayedMeasurementSystem != null) {
            viewModelScope.launch {
                val savedMeasurementSystemPref = getMeasurementSystemPrefFromSettingUseCase()
                if(displayedMeasurementSystem != savedMeasurementSystemPref)
                    submitCurrentWeatherSearch(displayedInput)
            }
        }
    }
}