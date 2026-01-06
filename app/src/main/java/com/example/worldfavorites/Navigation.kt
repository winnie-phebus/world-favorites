package com.example.worldfavorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.worldfavorites.view.HomeScreen
import com.example.worldfavorites.view.SearchCountryScreen
import com.example.worldfavorites.view.components.CountryDetailsBottomSheet
import com.example.worldfavorites.view.components.CountryItemState
import com.example.worldfavorites.view.viewmodel.HomeSceneViewModel
import com.example.worldfavorites.view.viewmodel.SearchCountrySceneViewModel
import kotlinx.serialization.Serializable

@Serializable
sealed class Scene(val route: String) {
    @Serializable
    object HomeScene : Scene(route = "Home")

    @Serializable
    object SearchCountryScene : Scene(route = "SearchCountry")

    @Serializable
    object CountryDetailsScene : Scene(route = "CountryDetails")
}

fun NavGraphBuilder.homeDestination(onNavAction: () -> Unit) {
    composable(Scene.HomeScene.route) {
        val viewModel: HomeSceneViewModel = hiltViewModel()

        HomeScreen(
            onNavEvent = onNavAction,
            viewModel = viewModel
        )
    }
}

fun NavController.navigateToHome() {
    navigate(route = Scene.HomeScene.route)
}

fun NavGraphBuilder.searchCountryDestination(onNavAction: () -> Unit) {
    composable(Scene.SearchCountryScene.route) {
        val viewModel: SearchCountrySceneViewModel = hiltViewModel()
        val viewState by viewModel.viewState.collectAsStateWithLifecycle()
        var showBottomSheet by remember { mutableStateOf(false) }
        var selectedCountry by remember { mutableStateOf<CountryItemState?>(null) }

        SearchCountryScreen(
            viewState = viewState,
            onNavAction = onNavAction,
            onCountryClick = { country ->
                selectedCountry = country
                showBottomSheet = true
            }
        )

        if (showBottomSheet) {
            CountryDetailsBottomSheet(
                country = selectedCountry,
                isEditMode = false,
                onDismiss = {
                    showBottomSheet = false
                    selectedCountry = null
                }
            )
        }
    }
}

fun NavController.navigateToSearchCountry() {
    navigate(route = Scene.SearchCountryScene.route)
}
