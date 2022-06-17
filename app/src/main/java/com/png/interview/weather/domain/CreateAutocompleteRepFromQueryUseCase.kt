package com.png.interview.weather.domain

import com.png.interview.api.common_model.NetworkResponse
import com.png.interview.weather.domain.conversion.AutocompleteResponseItemConversionFactory.convertItemsToSuggestions
import com.png.interview.weather.ui.model.AutocompleteViewRepresentation
import com.png.interview.weather.ui.model.AutocompleteViewRepresentation.AutocompleteViewRep
import com.png.interview.weather.ui.model.AutocompleteViewRepresentation.Error
import javax.inject.Inject

interface CreateAutocompleteRepFromQueryUseCase {
    suspend operator fun invoke(query: String): AutocompleteViewRepresentation
}

class DefaultCreateAutocompleteRepFromQueryUseCase @Inject constructor(
    private val getAutocompleteDataUseCase: GetAutocompleteDataUseCase
): CreateAutocompleteRepFromQueryUseCase {
    override suspend fun invoke(query: String): AutocompleteViewRepresentation =
        when(val result = getAutocompleteDataUseCase(query)) {
            is NetworkResponse.Success ->  {
                val suggestions = convertItemsToSuggestions(result.body)
                AutocompleteViewRep(suggestions)
            }
            else -> Error
        }
}