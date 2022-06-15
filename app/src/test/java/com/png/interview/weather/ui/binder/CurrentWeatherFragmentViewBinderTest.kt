package com.png.interview.weather.ui.binder

import android.app.Activity
import com.nhaarman.mockitokotlin2.*
import com.png.interview.BaseCoroutineTest
import com.png.interview.weather.domain.CreateCurrentWeatherRepFromQueryUseCase
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class CurrentWeatherFragmentViewBinderTest: BaseCoroutineTest() {
    private val activity: Activity = mock()
    private val settingsAction: () -> Unit = mock()
    private val forecastAction: (forecastQuery: String) -> Unit = mock()

    private val createCurrentWeatherRepFromQueryUseCase: CreateCurrentWeatherRepFromQueryUseCase = mock()
    private val viewModel = CurrentWeatherViewModel(
        createCurrentWeatherRepFromQueryUseCase
    )

    private val testObject = CurrentWeatherFragmentViewBinder(
        viewModel,
        activity,
        settingsAction,
        forecastAction
    )

    @Test
    fun `when refresh is clicked and the displayed input is an invalid length then does not resubmit request`() {
        dispatcher.runBlockingTest {
            testObject.refreshClicked()

            verify(createCurrentWeatherRepFromQueryUseCase, never()).invoke(any())
        }
    }

    @Test
    fun `when refresh is clicked and the displayed input IS a valid length then DOES resubmit request`() {
        dispatcher.runBlockingTest {
            val testInput = "5111"
            testObject.input = testInput

            testObject.goClicked()
            testObject.refreshClicked()

            verify(createCurrentWeatherRepFromQueryUseCase, times(2)).invoke(testInput)
        }
    }

    @Test
    fun `when refresh is clicked after the input text is changed to an invalid length then resubmits last valid input request`() {
        dispatcher.runBlockingTest {
            val testInput = "5111"
            testObject.input = testInput

            testObject.goClicked()
            testObject.refreshClicked()
            val invalidInput = "12"
            testObject.input = invalidInput
            testObject.refreshClicked()

            verify(createCurrentWeatherRepFromQueryUseCase, times(3)).invoke(testInput)
            verify(createCurrentWeatherRepFromQueryUseCase, never()).invoke(invalidInput)
        }
    }

    @Test
    fun `when forecast is selected then the forecast action is called with the display input past in`() {
        dispatcher.runBlockingTest {
            val testInput = "5111"
            testObject.input = testInput

            testObject.goClicked()
            testObject.seeForecastClicked()

            verify(forecastAction).invoke(testInput)
        }
    }
}