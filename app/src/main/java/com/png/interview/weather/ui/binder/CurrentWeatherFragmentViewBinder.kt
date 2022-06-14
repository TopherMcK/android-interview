package com.png.interview.weather.ui.binder

import android.app.Activity
import android.widget.Toast
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel

class CurrentWeatherFragmentViewBinder(
    private val viewModel: CurrentWeatherViewModel,
    private val activity: Activity,
    private val settingsAction: () -> Unit
) {

    val availableWeatherViewData = viewModel.availableCurrentWeatherLiveData
    val isEmpty = viewModel.isEmptyVisible
    val isQueryError = viewModel.isQueryErrorMsgVisible

    var input: String = ""
    private var displayedInput = ""

    fun refreshClicked() {
        if(displayedInput.length >= 3) viewModel.submitCurrentWeatherSearch(displayedInput)
    }

    fun seeForecastClicked() {
        Toast.makeText(activity, "Forecast Clicked TODO", Toast.LENGTH_LONG).show()
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
            displayedInput = input
            viewModel.submitCurrentWeatherSearch(input)
        }
    }
}