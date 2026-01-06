package com.example.worldfavorites

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            NavHost(
                modifier = Modifier,
                navController = navController,
                startDestination = Scene.HomeScene.route
            ) {
                homeDestination { navController.navigateToSearchCountry() }
                searchCountryDestination { navController.navigateToHome() }
            }
        }
    }
}