package com.png.interview.weather.ui.binder

import androidx.lifecycle.LifecycleOwner
import com.png.interview.weather.ui.customview.ForecastRecyclerViewAdapter
import com.png.interview.weather.ui.viewmodel.ForecastViewModel

class ForecastFragmentViewBinder(
    private val viewModel: ForecastViewModel,
    private val forecastRecyclerViewAdapter: ForecastRecyclerViewAdapter,
    viewLifecycleOwner: LifecycleOwner
) {

    private val forecastViewData = viewModel.forecastViewDataLiveData.observe(
        viewLifecycleOwner, {
            forecastRecyclerViewAdapter.updateForecastViewDays(it ?: emptyList())
        }
    )

    fun loadForecastViewData(forecastQuery: String) {
        viewModel.submitForecastQuery(forecastQuery)
    }
}