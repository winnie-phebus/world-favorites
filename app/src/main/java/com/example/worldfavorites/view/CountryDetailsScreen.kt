package com.example.worldfavorites.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

// might make into a bottom sheet instead of a full on screen
// but might as well scope this out a lil initially for Task 1

data class CountryDetailsSceneState(
    val countryName: String
)

val sampleCountryDetailsSceneState = CountryDetailsSceneState(countryName = "USA")

@Composable
fun CountryDetailsScene(viewState: CountryDetailsSceneState, onNavEvent: () -> Unit) {
    Column(){
        Text(viewState.countryName)
    }
}
