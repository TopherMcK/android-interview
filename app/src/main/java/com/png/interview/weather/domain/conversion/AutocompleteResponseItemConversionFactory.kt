package com.png.interview.weather.domain.conversion

import com.png.interview.weather.api.model.AutcompleteResponseItem
import com.png.interview.weather.ui.model.AutocompleteViewData

internal object AutocompleteResponseItemConversionFactory {

    fun convertItemsToSuggestions(responseItems: List<AutcompleteResponseItem>): List<AutocompleteViewData> =
        responseItems.sortedBy { it.name }.take(5).map {
            AutocompleteViewData("${it.name}, ${it.region}, ${it.country}")
        }
}