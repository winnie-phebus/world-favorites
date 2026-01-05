package com.example.worldfavorites

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.worldfavorites.databinding.ActivityMainBinding
import com.example.worldfavorites.view.CountryDetailsScene
import com.example.worldfavorites.view.CountryDetailsSceneState
import com.example.worldfavorites.view.CountryItemState
import com.example.worldfavorites.view.HomeSceneViewModel
import com.example.worldfavorites.view.HomeScreen
import com.example.worldfavorites.view.SearchCountrySceneViewModel
import com.example.worldfavorites.view.SearchCountryScreen
import com.example.worldfavorites.view.sampleCountryDetailsSceneState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

//            AppTheme {
                NavHost(
                        modifier = Modifier,
                        navController = navController,
                        startDestination = Scene.HomeScene.route
                    ) {
                        homeDestination { navController.navigateToSearchCountry() }
                        searchCountryDestination { navController.navigateToHome() }
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

@OptIn(ExperimentalMaterial3Api::class)
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

        if (showBottomSheet && selectedCountry != null) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                    selectedCountry = null
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                CountryDetailsScene(
                    viewState = CountryDetailsSceneState(country = selectedCountry!!),
                    onNavEvent = {
                        showBottomSheet = false
                        selectedCountry = null
                    }
                )
            }
        }
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