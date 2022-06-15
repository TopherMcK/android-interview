package com.png.interview.weather.ui.binder

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.nhaarman.mockitokotlin2.*
import com.png.interview.BaseCoroutineTest
import com.png.interview.weather.ui.customview.ForecastRecyclerViewAdapter
import com.png.interview.weather.ui.model.ForecastDayViewData
import com.png.interview.weather.ui.viewmodel.ForecastViewModel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class ForecastFragmentViewBinderTest: BaseCoroutineTest() {
    private val forecastRecyclerViewAdapter: ForecastRecyclerViewAdapter = mock()
    private val forecastLiveData = mock<LiveData<List<ForecastDayViewData>?>>()
    private val viewModel = mock<ForecastViewModel>() {
        on { forecastViewDataLiveData } doReturn forecastLiveData
    }
    private val lifecycleOwner = mock<LifecycleOwner>()

    private val testObject = ForecastFragmentViewBinder(
        viewModel,
        forecastRecyclerViewAdapter,
        lifecycleOwner
    )

    @Test
    fun `when binder is created then forecast live data is observed`() {
        verify(forecastLiveData).observe(
            eq(lifecycleOwner),
            any()
        )
    }

    @Test
    fun `when loading forecast data then submits forecast query`() {
        dispatcher.runBlockingTest {
            val testQuery = "test"

            testObject.loadForecastViewData(testQuery)

            verify(viewModel).submitForecastQuery(testQuery)
        }
    }
}