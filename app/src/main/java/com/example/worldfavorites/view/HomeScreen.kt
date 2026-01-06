package com.example.worldfavorites.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.worldfavorites.view.components.CountryDetailsBottomSheet
import com.example.worldfavorites.view.components.CountryItem
import com.example.worldfavorites.view.components.CountryItemAction
import com.example.worldfavorites.view.components.CountryItemActionIcon
import com.example.worldfavorites.view.components.CountryItemState
import com.example.worldfavorites.view.viewmodel.HomeSceneViewModel
import com.example.worldfavorites.view.viewmodel.HomeScreenState

@Composable
fun HomeScreen(
    onNavEvent: () -> Unit,
    viewModel: HomeSceneViewModel
){
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    var showEditBottomSheet by remember { mutableStateOf(false) }
    var countryToEdit by remember { mutableStateOf<CountryItemState?>(null) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header with primary background extending into status bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ){
            Spacer(modifier = Modifier.height(248.dp))
            Text(
                text = "My Favorite Countries",
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.weight(.75f),
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontFeatureSettings = "smcp"
                )
            )
            IconButton(onClick = onNavEvent) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add favorite country",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        when (val state = viewState) {
            is HomeScreenState.Success -> {
                val favorites = state.favoriteCountries ?: emptyList()

                if (favorites.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Looks like you haven't found a favorite yet!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onNavEvent) {
                            Text("Start Searching")
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp)
                    ) {
                        items(favorites) { country ->
                            CountryItem(
                                viewState = country,
                                onClick = {},
                                actions = listOf(
                                    CountryItemAction(
                                        icon = CountryItemActionIcon.EDIT,
                                        {
                                            countryToEdit = country
                                            showEditBottomSheet = true
                                        }
                                    ),
                                    CountryItemAction(
                                        icon = CountryItemActionIcon.DELETE,
                                        {
                                            viewModel.deleteFavoriteCountry(country.name)
                                        }
                                    )
                                )
                            )
                        }
                    }
                }
            }
            is HomeScreenState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.errorMessage.ifEmpty { "An error occurred!" },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            is HomeScreenState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

    if (showEditBottomSheet) {
        CountryDetailsBottomSheet(
            country = countryToEdit,
            isEditMode = true,
            onDismiss = {
                showEditBottomSheet = false
                countryToEdit = null
            }
        )
    }
}