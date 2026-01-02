package com.example.worldfavorites.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable

// gets countries through API: https://api.worldbank.org/v2/countries?format=json
// but for now? we using hard coded data! hell yeah

data class SearchCountryScreenState(
    val allCountries : List<CountryItemState>
)

val sampleAllCountries = listOf(
    CountryItemState("USA"),
    CountryItemState(name = "Canada"),
    CountryItemState(name = "France"),
    CountryItemState("Spain")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCountryScreen(viewState: SearchCountryScreenState) {
    // TODO: NAVIGATION - on country click, open CountryDetails Screen
    Column {
        // TODO: SEARCHBAR going to uncomment this later and focus on it when polishing. For now I just need a list of clickable Countries
//        SearchBar(
//            modifier = Modifier
//                .align(Alignment.TopCenter),
//            state = TODO(),
//            inputField = TODO(),
//            shape = TODO(),
//            colors = TODO(),
//            tonalElevation = TODO(),
//            shadowElevation = TODO()
//        )
        viewState.allCountries.forEach {
            Box{
                CountryItem(it) { }
            }
        }
    }
}