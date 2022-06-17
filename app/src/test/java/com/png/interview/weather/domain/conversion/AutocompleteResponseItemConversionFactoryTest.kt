package com.png.interview.weather.domain.conversion

import com.png.interview.weather.api.model.AutcompleteResponseItem
import com.png.interview.weather.domain.conversion.AutocompleteResponseItemConversionFactory.convertItemsToSuggestions
import com.png.interview.weather.ui.model.AutocompleteViewData
import org.junit.Assert.*
import org.junit.Test

class AutocompleteResponseItemConversionFactoryTest {

    @Test
    fun `when there are no items to convert then returns empty suggestions`() {
        assertTrue(convertItemsToSuggestions(emptyList()).isEmpty())
    }

    @Test
    fun `when there is a single items then returns a single suggestion`() {
        val responseItem = stubbedAutcompleteResponseItem()
        val expectedResult = listOf(responseItem.toAutocompleteViewData())

        val result = convertItemsToSuggestions(listOf(responseItem))
        assertEquals(result, expectedResult)
    }

    @Test
    fun `when converted an unsorted list of more than five items returns the first five alphabetically`() {
        val responseItemA = stubbedAutcompleteResponseItem(name = "a" )
        val responseItemB = stubbedAutcompleteResponseItem(name = "b")
        val responseItemC = stubbedAutcompleteResponseItem(name = "c")
        val responseItemD = stubbedAutcompleteResponseItem(name = "d")
        val responseItemE = stubbedAutcompleteResponseItem(name = "e")
        val responseItemF = stubbedAutcompleteResponseItem(name = "f")
        val responseItemG = stubbedAutcompleteResponseItem(name = "g")
        val expectedResult = listOf(
            responseItemA.toAutocompleteViewData(),
            responseItemB.toAutocompleteViewData(),
            responseItemC.toAutocompleteViewData(),
            responseItemD.toAutocompleteViewData(),
            responseItemE.toAutocompleteViewData()
        )

        val responseItems = listOf(
            responseItemF,
            responseItemA,
            responseItemG,
            responseItemB,
            responseItemE,
            responseItemC,
            responseItemD
        )
        val result = convertItemsToSuggestions(responseItems)
        assertEquals(result, expectedResult)
    }

    private fun AutcompleteResponseItem.toAutocompleteViewData() =
        AutocompleteViewData("$name, $region, $country")

    private fun stubbedAutcompleteResponseItem(
        country: String = "country",
        id: Int = 1,
        lat: Double = 1.1,
        lon: Double = 1.1,
        name: String = "name",
        region: String = "region",
        url: String = "url"
    ) =
        AutcompleteResponseItem(
            country,
            id,
            lat,
            lon,
            name,
            region,
            url
        )
}