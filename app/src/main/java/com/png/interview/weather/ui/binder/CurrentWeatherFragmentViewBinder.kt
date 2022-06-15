package com.png.interview.weather.ui.binder

import android.app.Activity
import android.widget.Toast
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel

class CurrentWeatherFragmentViewBinder(
    private val viewModel: CurrentWeatherViewModel,
    private val activity: Activity,
    private val settingsAction: () -> Unit,
    private val forecastAction: (forecastQuery: String) -> Unit
) {

    val availableWeatherViewData = viewModel.availableCurrentWeatherLiveData
    val isEmpty = viewModel.isEmptyVisible
    val isQueryError = viewModel.isQueryErrorMsgVisible

    var input: String = ""

    fun refreshClicked() {
        if(viewModel.displayedInput.length >= 3) viewModel.submitCurrentWeatherSearch(viewModel.displayedInput)
    }

    fun seeForecastClicked() {
        if(viewModel.displayedInput.length >= 3) forecastAction(viewModel.displayedInput)
    }

    fun settingsClicked() {
        settingsAction()
    }

    fun goClicked() {
        if (input.isEmpty()) {
            Toast.makeText(activity, "Please Enter Query", Toast.LENGTH_LONG).show()
        } else if (input.length < 3) {
            Toast.makeText(activity, "Please Enter More than 3 Characters", Toast.LENGTH_LONG).show()
        } else {
            viewModel.displayedInput = input
            viewModel.submitCurrentWeatherSearch(input)
        }
    }
}