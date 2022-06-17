package com.png.interview.weather.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.png.interview.BaseCoroutineTest
import com.png.interview.weather.api.WeatherApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class DefaultGetAutocompleteDataUseCaseTest: BaseCoroutineTest() {

    private val weatherApi = mock<WeatherApi>()

    private val testObject = DefaultGetAutocompleteDataUseCase(weatherApi)

    @Test
    fun `when calling to get autocomplete data then the weather api is called`() {
        dispatcher.runBlockingTest {
            val query = "test"

            testObject.invoke(query)

            verify(weatherApi).getAutocompleteResults(query)
        }
    }
}