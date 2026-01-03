package com.example.worldfavorites.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// this screen shows the list of user favorites, and also enables navigation to the SearchCountryScreen

// ideally the list will be pulled from room, but for now let's hard code a sample

// Country State & Status
enum class CountryItemStatus{
    FAVORITE, NONE
}

data class CountryItemState (
    val name: String,
    val description: String? = null,
    val status: CountryItemStatus = CountryItemStatus.NONE
)

@Composable
fun CountryItem(viewState: CountryItemState, onClick: () -> Unit ){
    Column(
        modifier = Modifier
            .padding(horizontal = 2.dp, vertical = 5.dp)
            .clickable(onClick = onClick)
    ) {
        Text(viewState.name, style = MaterialTheme.typography.titleSmall)
        viewState.description?.let{
            Text(it, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

val sampleCountryItemStates = listOf(
    CountryItemState(
        name = "United States of America",
        description = "Where I live!",
        status = CountryItemStatus.FAVORITE
    ),
    CountryItemState(
        name = "Jamaica"
    )
)

@Preview
@Composable
fun CountryItemPreview(){

    Column {
        sampleCountryItemStates.forEach {
            CountryItem(it, {})
        }
    }
}
// Home Screen State & View
data class HomeScreenState(
    val favoriteCountries : List<CountryItemState>
)
//fun HomeScreen ()
@Composable
fun HomeScreen(
    viewState: HomeScreenState,
    onNavEvent: () -> Unit
){
    Column() {
        Text(
            "My Favorite Countries",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
//            fontStyle = FontStyle.
            )
        viewState.favoriteCountries.forEach { country ->
            CountryItem(country, {})
        }
        IconButton(onClick = onNavEvent) {
            Text("Add Country")
        }
    }
}

val sampleHomeScreenState =  HomeScreenState(
    favoriteCountries = sampleCountryItemStates
)

@Composable
@Preview
fun HomeScreenPreview(){
    val sampleHomeScreens = listOf(
        sampleHomeScreenState
    )

    Column(){
        sampleHomeScreens.forEach { HomeScreen(it, {}) }
    }
}