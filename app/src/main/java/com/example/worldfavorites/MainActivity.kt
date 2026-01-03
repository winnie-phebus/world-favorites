package com.example.worldfavorites

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.worldfavorites.databinding.ActivityMainBinding
import com.example.worldfavorites.view.CountryDetailsScene
import com.example.worldfavorites.view.HomeScreen
import com.example.worldfavorites.view.SearchCountryScreen
import com.example.worldfavorites.view.SearchCountryScreenState
import com.example.worldfavorites.view.sampleAllCountries
import com.example.worldfavorites.view.sampleCountryDetailsSceneState
import com.example.worldfavorites.view.sampleHomeScreenState
import kotlinx.serialization.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

//            AppTheme {
                NavHost(
                        modifier = Modifier,
                        navController = navController,
                        startDestination = Scene.HomeScene.route
                    ) {
                        homeDestination { navController.navigateToSearchCountry() }
                        searchCountryDestination { navController.navigateToCountryDetails() }
                        countryDetailsDestination { navController.navigateToCountryDetails() }
                    }
                }
//            }
        }
//
//
////        binding = ActivityMainBinding.inflate(layoutInflater)
////        setContentView(binding.root)
////
////        setSupportActionBar(binding.toolbar)
////
////        val navController = findNavController(R.id.nav_host_fragment_content_main)
////        appBarConfiguration = AppBarConfiguration(navController.graph)
////        setupActionBarWithNavController(navController, appBarConfiguration)
////
////        binding.fab.setOnClickListener { view ->
////            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                .setAction("Action", null)
////                .setAnchorView(R.id.fab).show()
////        }
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
}

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
        // TODO: ARCH - include ViewModel here

        // example:
        // val viewModel: ContactsViewModel = hiltViewModel()
        // val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        HomeScreen(
            sampleHomeScreenState,
            onNavAction
        )
    }
}

fun NavController.navigateToHome() {
    navigate(route = Scene.HomeScene.route)
}

fun NavGraphBuilder.searchCountryDestination(onNavAction: () -> Unit) {
    composable(Scene.SearchCountryScene.route) {
        // TODO: ARCH - include ViewModel here

        // example:
        // val viewModel: ContactsViewModel = hiltViewModel()
        // val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        SearchCountryScreen(
            SearchCountryScreenState(
                allCountries = sampleAllCountries
            ),
            onNavAction
        )
    }
}

fun NavController.navigateToSearchCountry() {
    navigate(route = Scene.SearchCountryScene.route)
}

fun NavGraphBuilder.countryDetailsDestination(onNavAction: () -> Unit) {
    composable(Scene.CountryDetailsScene.route) {
        // TODO: ARCH - include ViewModel here

        // example:
        // val viewModel: ContactsViewModel = hiltViewModel()
        // val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        CountryDetailsScene(
            sampleCountryDetailsSceneState,
            onNavEvent = {}
        )
    }
}

fun NavController.navigateToCountryDetails() {
    navigate(route = Scene.CountryDetailsScene.route)
}