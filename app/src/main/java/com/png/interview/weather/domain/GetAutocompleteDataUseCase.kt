package com.png.interview.weather.domain

import com.png.interview.api.common_model.NetworkResponse
import com.png.interview.weather.api.WeatherApi
import com.png.interview.weather.api.model.AutcompleteResponseItem
import javax.inject.Inject

interface GetAutocompleteDataUseCase {
    suspend operator fun invoke(query: String): NetworkResponse<List<AutcompleteResponseItem>, Unit>
}

class DefaultGetAutocompleteDataUseCase @Inject constructor(
    private val weatherApi: WeatherApi
): GetAutocompleteDataUseCase {
    override suspend fun invoke(query: String): NetworkResponse<List<AutcompleteResponseItem>, Unit> =
        weatherApi.getAutocompleteResults(query)
}