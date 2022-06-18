package com.png.interview.weather.ui.binder

import android.app.Activity
import android.text.Editable
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.png.interview.weather.ui.customview.AutoCompleteListArrayAdapter
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel

class CurrentWeatherFragmentViewBinder(
    private val viewModel: CurrentWeatherViewModel,
    private val activity: Activity,
    private val settingsAction: () -> Unit,
    private val forecastAction: (forecastQuery: String) -> Unit,
    lifecycleOwner: LifecycleOwner,
    autocompleteAdapter: AutoCompleteListArrayAdapter
) {

    val availableWeatherViewData = viewModel.availableCurrentWeatherLiveData
    val isEmpty = viewModel.isEmptyVisible
    val isQueryError = viewModel.isQueryErrorMsgVisible
    val hasAutocompleteSuggestions = viewModel.hasAutocompleteSuggestionsVisible
    private val autocompleteViewData = viewModel.autocompleteLiveData.observe(
        lifecycleOwner, { newSuggestionsData ->
            val newSuggestions = newSuggestionsData?.map { it.suggestion } ?: emptyList()
            autocompleteAdapter.updateData(newSuggestions)
        }
    )

    var input: String = ""

    fun refreshClicked() {
        if(viewModel.displayedInput.length >= 3) {
            viewModel.setAutocompleteEmpty()
            viewModel.submitCurrentWeatherSearch(viewModel.displayedInput)
        }
    }

    fun seeForecastClicked() {
        if(viewModel.displayedInput.length >= 3) {
            viewModel.setAutocompleteEmpty()
            forecastAction(viewModel.displayedInput)
        }
    }

    fun settingsClicked() {
        viewModel.setAutocompleteEmpty()
        settingsAction()
    }

    fun goClicked() {
        viewModel.setAutocompleteEmpty()
        if (input.isEmpty()) {
            Toast.makeText(activity, "Please Enter Query", Toast.LENGTH_LONG).show()
        } else if (input.length < 3) {
            Toast.makeText(activity, "Please Enter More than 3 Characters", Toast.LENGTH_LONG).show()
        } else {
            viewModel.displayedInput = input
            viewModel.submitCurrentWeatherSearch(input)
        }
    }

    fun suggestionItemClicked(position: Int) {
        viewModel.submitCurrentWeatherSearch(position)
        input = ""
        viewModel.setAutocompleteEmpty()
    }

    fun retrieveSuggestionsIfNeeded(e: Editable) {
        if (input.isBlank()) viewModel.setAutocompleteEmpty()

        if(input.length > 2) viewModel.submitAutocompleteQuery(input)
    }

    fun updateForMeasurementSystemChangeIFNeeded() {
        viewModel.updateForMeasurementSystemChangeIFNeeded()
    }
}