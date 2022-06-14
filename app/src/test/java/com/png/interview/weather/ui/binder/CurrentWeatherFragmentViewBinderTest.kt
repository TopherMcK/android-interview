package com.png.interview.weather.ui.binder

import android.app.Activity
import com.nhaarman.mockitokotlin2.*
import com.png.interview.weather.domain.CreateCurrentWeatherRepFromQueryUseCase
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CurrentWeatherFragmentViewBinderTest {
    private val activity: Activity = mock()
    private val settingsAction: () -> Unit = mock()

    private val createCurrentWeatherRepFromQueryUseCase: CreateCurrentWeatherRepFromQueryUseCase = mock()
    private val viewModel = CurrentWeatherViewModel(
        createCurrentWeatherRepFromQueryUseCase
    )

    private val testObject = CurrentWeatherFragmentViewBinder(
        viewModel,
        activity,
        settingsAction
    )

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when refresh is clicked and the displayed input is an invalid length then does not resubmit request`() {
        runBlocking {
            testObject.refreshClicked()

            verify(createCurrentWeatherRepFromQueryUseCase, never()).invoke(any())
        }
    }

    @Test
    fun `when refresh is clicked and the displayed input IS a valid length then DOES resubmit request`() {
        runBlocking {
            val testInput = "5111"
            testObject.input = testInput

            testObject.goClicked()
            testObject.refreshClicked()

            verify(createCurrentWeatherRepFromQueryUseCase, times(2)).invoke(testInput)
        }
    }

    @Test
    fun `when refresh is clicked after the input text is changed to an invalid length then resubmits last valid input request`() {
        runBlocking {
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
}