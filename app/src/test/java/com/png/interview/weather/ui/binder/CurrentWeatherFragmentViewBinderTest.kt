package com.png.interview.weather.ui.binder

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.nhaarman.mockitokotlin2.*
import com.png.interview.BaseCoroutineTest
import com.png.interview.weather.domain.CreateCurrentWeatherRepFromQueryUseCase
import com.png.interview.weather.ui.customview.AutoCompleteListArrayAdapter
import com.png.interview.weather.ui.model.AutocompleteViewData
import com.png.interview.weather.ui.model.AvailableWeatherViewData
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class CurrentWeatherFragmentViewBinderTest: BaseCoroutineTest() {
    private val activity: Activity = mock()
    private val settingsAction: () -> Unit = mock()
    private val forecastAction: (forecastQuery: String) -> Unit = mock()
    private val createCurrentWeatherRepFromQueryUseCase: CreateCurrentWeatherRepFromQueryUseCase = mock()
    private val lifecycleOwner: LifecycleOwner = mock()
    private val adapter: AutoCompleteListArrayAdapter = mock()
    private val availableWeatherLiveData = mock<LiveData<AvailableWeatherViewData?>>()
    private val autocompleteLiveData = mock<LiveData<List<AutocompleteViewData>?>>()
    private val viewModel: CurrentWeatherViewModel = mock {
        on { availableCurrentWeatherLiveData } doReturn availableWeatherLiveData
        on { autocompleteLiveData } doReturn autocompleteLiveData
    }

    private val testObject = CurrentWeatherFragmentViewBinder(
        viewModel,
        activity,
        settingsAction,
        forecastAction,
        lifecycleOwner,
        adapter
    )

    @Test
    fun `when refresh is clicked and the displayed input is an invalid length then does not resubmit request`() {
        dispatcher.runBlockingTest {
            whenever(viewModel.displayedInput).thenReturn("qq")

            testObject.refreshClicked()

            verify(createCurrentWeatherRepFromQueryUseCase, never()).invoke(any())
        }
    }

    @Test
    fun `when refresh is clicked and the displayed input IS a valid length then DOES resubmit request`() {
        dispatcher.runBlockingTest {
            val testInput = "5111"
            testObject.input = testInput

            whenever(viewModel.displayedInput).thenReturn(testInput)

            testObject.goClicked()
            testObject.refreshClicked()

            verify(viewModel, times(2)).setAutocompleteEmpty()
            verify(viewModel).displayedInput = testInput
            verify(viewModel, times(2)).submitCurrentWeatherSearch(testInput)
        }
    }

    @Test
    fun `when refresh is clicked after the input text is changed to an invalid length then resubmits last valid input request`() {
        dispatcher.runBlockingTest {
            val testInput = "5111"
            testObject.input = testInput

            whenever(viewModel.displayedInput).thenReturn(testInput)

            testObject.goClicked()
            testObject.refreshClicked()
            val invalidInput = "12"
            testObject.input = invalidInput
            testObject.refreshClicked()

            verify(viewModel, times(3)).setAutocompleteEmpty()
            verify(viewModel).displayedInput = testInput
            verify(viewModel, times(3)).submitCurrentWeatherSearch(testInput)

            verify(viewModel, never()).submitCurrentWeatherSearch(invalidInput)
        }
    }

    @Test
    fun `when forecast is selected then the forecast action is called with the display input past in`() {
        dispatcher.runBlockingTest {
            val testInput = "5111"
            testObject.input = testInput

            whenever(viewModel.displayedInput).thenReturn(testInput)

            testObject.goClicked()
            testObject.seeForecastClicked()

            verify(forecastAction).invoke(testInput)
        }
    }
}