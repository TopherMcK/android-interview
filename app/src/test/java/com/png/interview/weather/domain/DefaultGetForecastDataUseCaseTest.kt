package com.png.interview.weather.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.png.interview.BaseCoroutineTest
import com.png.interview.weather.api.WeatherApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultGetForecastDataUseCaseTest: BaseCoroutineTest() {
    private val weatherApi: WeatherApi = mock()

    private val testObject = DefaultGetForecastDataUseCase(weatherApi)

    @Test
    fun `when getting a forecast then requests forecast for the next three days`() {
        dispatcher.runBlockingTest {
            val testQuery = "test"
            testObject(testQuery)

            verify(weatherApi).getForecast(testQuery, 3)
        }
    }
}